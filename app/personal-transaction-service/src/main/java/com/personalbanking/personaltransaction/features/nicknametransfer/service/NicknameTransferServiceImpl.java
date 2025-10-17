package com.personalbanking.personaltransaction.features.nicknametransfer.service;

import com.personalbanking.personaltransaction.features.nicknametransfer.models.Transaction;
import com.personalbanking.personaltransaction.features.nicknametransfer.repository.NicknameTransferRepository;
import com.personalbanking.personaltransaction.proto.nickname_transfer.NicknameTransferServiceGrpc;
import com.personalbanking.personaltransaction.proto.nickname_transfer.PrepareTransferByNicknameRequest;
import com.personalbanking.personaltransaction.proto.nickname_transfer.PrepareTransferByNicknameResponse;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@GrpcService
public class NicknameTransferServiceImpl extends NicknameTransferServiceGrpc.NicknameTransferServiceImplBase {

    private final NicknameTransferRepository nicknameTransferRepository;
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public NicknameTransferServiceImpl(NicknameTransferRepository nicknameTransferRepository) {
        this.nicknameTransferRepository = nicknameTransferRepository;
    }

    @Override
    public void prepareTransferByNickname(PrepareTransferByNicknameRequest request,
                                          StreamObserver<PrepareTransferByNicknameResponse> responseObserver) {
        try {
            // TODO: Extract from metadata
            Long currentUserId = 123L;

            if(request.getNicknameId() <= 0){
                throw status(Status.INVALID_ARGUMENT, "Nickname ID must be positive");
            }

            var nicknameOpt = nicknameTransferRepository.findNicknameById(request.getNicknameId());
            if (nicknameOpt.isEmpty()) {
                throw status(Status.NOT_FOUND, "Nickname not found");
            }

            var toAccountOpt = nicknameTransferRepository.findAccountByNicknameId(request.getNicknameId());
            if (toAccountOpt.isEmpty()) {
                throw status(Status.NOT_FOUND, "No account found for the given nickname");
            }

            var fromAccountOpt = nicknameTransferRepository.findAccountByUserId(currentUserId);
            if (fromAccountOpt.isEmpty()) {
                throw status(Status.NOT_FOUND, "User account not found");
            }

            var fromAccount = fromAccountOpt.get();
            var toAccount = toAccountOpt.get();

            if (fromAccount.id().equals(toAccount.id())) {
                throw status(Status.FAILED_PRECONDITION, "Cannot transfer to the same account");
            }

            String externalTxnId = nicknameTransferRepository.prepareTransfer(
                    fromAccount.id(),
                    toAccount.id(),
                    currentUserId
            );

            var txn = new Transaction(
                    null,
                    toAccount.id(),
                    fromAccount.id(),
                    BigDecimal.ZERO,
                    "PREPARED",
                    LocalDateTime.now().format(DT_FMT),
                    LocalDateTime.now().format(DT_FMT)
            );

            nicknameTransferRepository.saveTransaction(txn);

            var response = PrepareTransferByNicknameResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Transfer prepared successfully")
                    .setTransactionId(externalTxnId)
                    .setStatus("PREPARED")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (StatusRuntimeException sre) {
            responseObserver.onError(sre);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Internal server error")
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    private StatusRuntimeException status(Status s, String msg) {
        return s.withDescription(msg).asRuntimeException();
    }
}

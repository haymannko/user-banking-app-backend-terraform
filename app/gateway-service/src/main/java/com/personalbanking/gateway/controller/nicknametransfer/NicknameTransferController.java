package com.personalbanking.gateway.controller.nicknametransfer;

import com.personalbanking.gateway.dto.nicknametransfer.PrepareTransferByNicknameRequestDto;
import com.personalbanking.gateway.dto.nicknametransfer.PrepareTransferByNicknameResponseDto;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/personal-banking/transfer/nickname")
public class NicknameTransferController {

    @GrpcClient("personal-transaction-service")
    private com.personalbanking.personaltransaction.proto.nickname_transfer.NicknameTransferServiceGrpc.NicknameTransferServiceBlockingStub nicknameTransferService;

    @PostMapping("/prepare")
    public ResponseEntity<PrepareTransferByNicknameResponseDto> prepareTransfer(@RequestBody PrepareTransferByNicknameRequestDto requestDto) {

        com.personalbanking.personaltransaction.proto.nickname_transfer.PrepareTransferByNicknameRequest request = com.personalbanking.personaltransaction.proto.nickname_transfer.PrepareTransferByNicknameRequest.newBuilder()
                .setNicknameId(requestDto.nicknameId())
                .build();
        try {
            com.personalbanking.personaltransaction.proto.nickname_transfer.PrepareTransferByNicknameResponse response = nicknameTransferService.prepareTransferByNickname(request);

            PrepareTransferByNicknameResponseDto responseDto = toDto(response);

            return ResponseEntity.ok(responseDto);
        } catch (StatusRuntimeException e) {
            HttpStatus http = switch (e.getStatus().getCode()) {
                case INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST;
                case NOT_FOUND -> HttpStatus.NOT_FOUND;
                case PERMISSION_DENIED, UNAUTHENTICATED -> HttpStatus.UNAUTHORIZED;
                case FAILED_PRECONDITION -> HttpStatus.CONFLICT;
                case UNAVAILABLE, DEADLINE_EXCEEDED -> HttpStatus.SERVICE_UNAVAILABLE;
                default -> HttpStatus.INTERNAL_SERVER_ERROR;
            };
            PrepareTransferByNicknameResponseDto err = new PrepareTransferByNicknameResponseDto(
                    false,
                    e.getStatus().getDescription() != null ? e.getStatus().getDescription() : "gRPC call failed",
                    null,
                    e.getStatus().getCode().name()
            );
            return ResponseEntity.status(http).body(err);
        }
    }

    private PrepareTransferByNicknameResponseDto toDto(com.personalbanking.personaltransaction.proto.nickname_transfer.PrepareTransferByNicknameResponse response){
        return new PrepareTransferByNicknameResponseDto(
                response.getSuccess(),
                response.getMessage(),
                response.getTransactionId(),
                response.getStatus()
        );
    }

}

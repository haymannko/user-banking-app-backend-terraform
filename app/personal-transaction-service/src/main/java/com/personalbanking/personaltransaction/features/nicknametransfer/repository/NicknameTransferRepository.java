package com.personalbanking.personaltransaction.features.nicknametransfer.repository;

import com.personalbanking.personaltransaction.features.nicknametransfer.models.AccountDetail;
import com.personalbanking.personaltransaction.features.nicknametransfer.models.Nickname;
import com.personalbanking.personaltransaction.features.nicknametransfer.models.Transaction;

import java.util.Optional;

public interface NicknameTransferRepository {
    Optional<Nickname> findNicknameById(Long nicknameId);
    Optional<AccountDetail> findAccountByNicknameId(Long nicknameId);
    Optional<AccountDetail> findAccountByUserId(Long userId);
    String prepareTransfer(Long fromAccountId, Long toAccountId, Long userId);
    Long saveTransaction(Transaction transaction);
}

package com.personalbanking.personaltransaction.features.nicknametransfer.models;

import java.math.BigDecimal;

public record AccountDetail(
        Long id,
        Long userId,
        String accountNumber,
        Long accountTypeId,
        Long groupId,
        BigDecimal currentBalance,
        Long roleId,
        Long nicknameId,
        Long kycId,
        String createdAt,
        String updatedAt
) {}

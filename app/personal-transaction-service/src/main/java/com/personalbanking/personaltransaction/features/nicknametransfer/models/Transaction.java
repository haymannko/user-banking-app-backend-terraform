package com.personalbanking.personaltransaction.features.nicknametransfer.models;

import org.springframework.cglib.core.Local;

import java.math.BigDecimal;

public record Transaction(
        Long id,
        Long creditAccountId,
        Long debitAccountId,
        BigDecimal amount,
        String status,
        String createdAt,
        String updatedAt
) {}

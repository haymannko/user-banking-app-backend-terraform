package com.personalbanking.gateway.dto.nicknametransfer;

public record PrepareTransferByNicknameResponseDto(
        boolean success,
        String message,
        String transactionId,
        String status
) {}

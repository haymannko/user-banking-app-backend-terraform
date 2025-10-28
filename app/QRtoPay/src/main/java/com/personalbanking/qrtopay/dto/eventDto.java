package com.personalbanking.qrtopay.dto;

public record eventDto(
        String AccountID,
        Double amount,
        String accountName,
        String AccountNo
) {
}

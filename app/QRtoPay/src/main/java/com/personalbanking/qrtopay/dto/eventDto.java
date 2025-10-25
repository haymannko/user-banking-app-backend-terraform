package com.personalbanking.qrtopay.dto;

public record eventDto(
        String AccountID,
        Long AccountNumber,
        Double amount
) {
}

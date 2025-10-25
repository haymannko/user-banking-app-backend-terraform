package com.personalbanking.gateway.dto.scantopay;

public record prepareScanToPay(
        long accountID,
        Double  Amount,
        String  Note,
        String AccountNumber
) {
}

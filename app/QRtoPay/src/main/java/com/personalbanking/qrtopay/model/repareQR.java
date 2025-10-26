package com.personalbanking.qrtopay.model;

public record repareQR(
        String AccountNo,
        Long accountID,
        String QRCode
) {
}

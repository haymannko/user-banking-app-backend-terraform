package dto;

public record getQR(
        String qrCode,
        String accountId,
        long timestamp
) {
}

package service;

public interface paymnetSErvice {

    String pay(String accountId, String amount);
    String getQR(String accountId);
}

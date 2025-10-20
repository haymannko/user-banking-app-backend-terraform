package repository;

import service.paymnetSErvice;

public interface scantoPay {
    String scan(String accountId);
    scantoPay getScantoPay();
    getqrcode getgetqrcode();
    paymnetSErvice getpaymnetSErvice();
}

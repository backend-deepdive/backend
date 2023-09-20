package com.core;

public enum Exchange {
    UPBIT("upbit"),
    BITHUMB("bithumb"),
    KORBIT("korbit"),
    ;
    private String exchange;

    Exchange(String exchange) {
        this.exchange = exchange;
    }
}

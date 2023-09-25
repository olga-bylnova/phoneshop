package com.es.core.model.exception;

public class OutOfStockException extends Exception {
    private Long phoneId;
    private int stockRequested;
    private int stockAvailable;

    public Long getPhoneId() {
        return phoneId;
    }

    public int getStockRequested() {
        return stockRequested;
    }

    public int getStockAvailable() {
        return stockAvailable;
    }

    public OutOfStockException(Long phoneId, int stockRequested, int stockAvailable) {
        this.phoneId = phoneId;
        this.stockRequested = stockRequested;
        this.stockAvailable = stockAvailable;
    }
}
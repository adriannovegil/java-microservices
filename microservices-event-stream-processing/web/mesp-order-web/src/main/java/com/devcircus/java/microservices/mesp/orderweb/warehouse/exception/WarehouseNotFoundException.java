package com.devcircus.java.microservices.mesp.orderweb.warehouse.exception;

public class WarehouseNotFoundException extends RuntimeException {

    public WarehouseNotFoundException() {
    }

    public WarehouseNotFoundException(String message) {
        super(message);
    }

    public WarehouseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WarehouseNotFoundException(Throwable cause) {
        super(cause);
    }

    public WarehouseNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

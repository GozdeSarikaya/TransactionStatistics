package com.n26.exception;

public class TransactionExpiredException extends Exception {

    private static final long serialVersionUID = -4860883921334278347L;

    public TransactionExpiredException() {
        super();
    }

    public TransactionExpiredException(String message) {
        super(message);
    }

    public TransactionExpiredException(String message, Throwable t) {
        super(message, t);
    }
}

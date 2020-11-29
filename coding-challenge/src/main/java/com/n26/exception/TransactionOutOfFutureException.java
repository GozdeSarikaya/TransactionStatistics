package com.n26.exception;

public class TransactionOutOfFutureException extends Exception {

    private static final long serialVersionUID = -4860883921334278347L;

    public TransactionOutOfFutureException() {
        super();
    }

    public TransactionOutOfFutureException(String message) {
        super(message);
    }

    public TransactionOutOfFutureException(String message, Throwable t) {
        super(message, t);
    }
}

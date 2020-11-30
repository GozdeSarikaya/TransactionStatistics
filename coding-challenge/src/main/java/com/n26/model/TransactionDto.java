package com.n26.model;

import java.time.Instant;

public class TransactionDto {

    private double amount;
    private Instant timestamp;

    public double getAmount() {
        return amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

}

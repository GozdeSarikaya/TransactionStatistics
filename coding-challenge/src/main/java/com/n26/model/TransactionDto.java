package com.n26.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;

//TransactionDTO ?
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {

    private double amount;
    private Instant timestamp;

    //Parametered constructor?


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

    @Override
    public String toString() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "{}";
        }
    }
}

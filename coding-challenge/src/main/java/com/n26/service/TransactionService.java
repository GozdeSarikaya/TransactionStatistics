package com.n26.service;

import com.n26.exception.TransactionServiceException;
import com.n26.model.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TransactionService {

    @Autowired
    private StatisticsService statisticsService;

    public static final int TIME_LIMIT = 600;

    public void addTransaction(TransactionDto transaction) throws TransactionServiceException {
/*

        long transactionAge = (System.currentTimeMillis() - transaction.getTimestamp()) / 1000;

        if (transactionAge > TIME_LIMIT) {

            ZonedDateTime transactionTime = Instant.ofEpochMilli(transaction.getTimestamp()).atZone(ZoneId.systemDefault());
            throw new TransactionServiceException("Transaction is older than 60 seconds. Transaction time - " + transactionTime);
        }
*/

        this.statisticsService.addTransaction(transaction);
    }

    public void deleteAllTransactions() {
        this.statisticsService.removeAllTransactions();
    }

}

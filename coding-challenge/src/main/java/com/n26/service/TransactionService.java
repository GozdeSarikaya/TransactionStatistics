package com.n26.service;

import com.n26.exception.TransactionExpiredException;
import com.n26.exception.TransactionOutOfFutureException;
import com.n26.model.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
public class TransactionService {

    @Autowired
    private StatisticsService statisticsService;

    public static final int TIME_LIMIT = 60;

    public void addTransaction(TransactionDto transaction) throws TransactionExpiredException, TransactionOutOfFutureException {

        long currenttime = Instant.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        long timediff = currenttime - transaction.getTimestamp().toEpochMilli() / 1000;
        if (timediff < 0)
            throw new TransactionOutOfFutureException("Transaction is in the future");

        if (timediff >= TIME_LIMIT)
            throw new TransactionExpiredException("Transaction is older than 60 seconds.");

        this.statisticsService.addTransaction(transaction);
    }

    public void deleteAllTransactions() {
        this.statisticsService.removeAllTransactions();
    }

}

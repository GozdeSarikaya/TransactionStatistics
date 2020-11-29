package com.n26.service;

import com.n26.exception.TransactionExpiredException;
import com.n26.exception.TransactionOutOfFutureException;
import com.n26.model.TransactionDto;
import com.n26.utils.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Value("${TIME_LIMIT_INSEC}")
    private long TIME_LIMIT_INSEC;

    @Autowired
    private StatisticsService statisticsService;

    public void addTransaction(TransactionDto transaction) throws TransactionExpiredException, TransactionOutOfFutureException {

        long timediff = AppUtility.CalculateTimeDiffs(transaction.getTimestamp());
        if (timediff < 0)
            throw new TransactionOutOfFutureException("Transaction is in the future");

        if (timediff >= TIME_LIMIT_INSEC)
            throw new TransactionExpiredException("Transaction is older than 60 seconds.");

        this.statisticsService.addTransaction(transaction);
    }

    public void deleteAllTransactions() {
        this.statisticsService.removeAllTransactions();
    }

}

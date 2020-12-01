package com.n26.service;

import com.n26.exception.TransactionExpiredException;
import com.n26.exception.TransactionOutOfFutureException;
import com.n26.model.TransactionDto;
import com.n26.utils.AppUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Value("${TIME_LIMIT_INSEC}")
    private long TIME_LIMIT_INSEC;

    @Autowired
    private StatisticsService statisticsService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public void addTransaction(TransactionDto transaction) throws TransactionExpiredException, TransactionOutOfFutureException {

        long timediff = AppUtility.CalculateTimeDiffs(transaction.getTimestamp());
        logger.debug("Time Difference between transaction and current time in ms: {}", timediff);
        if (timediff < 0)
            throw new TransactionOutOfFutureException("Transaction is in the future");

        if (timediff >= TIME_LIMIT_INSEC)
            throw new TransactionExpiredException("Transaction is older than 60 seconds.");

        logger.debug("The transaction will be added to statistics service");
        this.statisticsService.addTransaction(transaction);
        logger.debug("The transaction is successfully added to statistics service");
    }

    public void deleteAllTransactions() {
        this.statisticsService.removeAllTransactions();
    }

}

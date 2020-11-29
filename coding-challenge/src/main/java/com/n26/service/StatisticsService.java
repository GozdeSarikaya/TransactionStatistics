package com.n26.service;

import com.n26.model.StatisticsDto;
import com.n26.model.TransactionDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StatisticsService {

    private Map<Instant, TransactionDto> transactionMap;

    public StatisticsService() {
        this.transactionMap = new ConcurrentHashMap<>();
    }

    public Map<Instant, TransactionDto> getTransactionMap() {
        return transactionMap;
    }

    public void addTransaction(TransactionDto transaction) {
        this.getTransactionMap().put(transaction.getTimestamp(), transaction);
    }

    public void removeAllTransactions() {
        this.getTransactionMap().clear();
    }

    public StatisticsDto getStatistics() {
        return new StatisticsDto();
    }
}

package com.n26.service;

import com.n26.model.StatisticsDto;
import com.n26.model.TransactionDto;
import com.n26.utils.AppUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class StatisticsService {

    @Value("${TIME_LIMIT_INSEC}")
    private long TIME_LIMIT_INSEC;
    private static final List<TransactionDto> transaction_map = new ArrayList<>();
    private StatisticsDto stats = new StatisticsDto();
    private final Object LOCK = new Object();
    private static final Logger logger = LoggerFactory.getLogger(StatisticsService.class);
    public void addTransaction(TransactionDto transaction) {
        synchronized (LOCK) {
            transaction_map.add(transaction);
            calculateStatistics();
        }
    }

    public void removeAllTransactions() {
        transaction_map.clear();
        stats = new StatisticsDto();
    }

    public StatisticsDto getStatistics() {
        return stats;
    }

    @Async
    public void calculateStatistics() {
        logger.debug("Statistics will be calculated");
        if (transaction_map.size() == 0) {
            stats = new StatisticsDto();
        } else {
            DoubleSummaryStatistics stat = transaction_map.stream().mapToDouble(TransactionDto::getAmount).summaryStatistics();
            stats = new StatisticsDto(AppUtility.ToBigDecimal(stat.getSum()), AppUtility.ToBigDecimal(stat.getAverage()), AppUtility.ToBigDecimal(stat.getMax()), AppUtility.ToBigDecimal(stat.getMin()), stat.getCount());
        }
        logger.debug("Statistics is successfully calculated");
    }


    @Scheduled(fixedRate = 100, initialDelay = 0)
    public void discardOldTransactions() {
        synchronized (LOCK) {
            if (transaction_map.size() > 0) {
                transaction_map.removeIf(transaction -> AppUtility.CalculateTimeDiffs(transaction.getTimestamp()) >= TIME_LIMIT_INSEC);
                calculateStatistics();
            }
        }
    }
}

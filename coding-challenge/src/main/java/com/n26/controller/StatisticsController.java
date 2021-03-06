package com.n26.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.n26.model.StatisticsDto;
import com.n26.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public StatisticsDto getStatistics() {
        StatisticsDto statistics = statisticsService.getStatistics();
        logger.debug("Returning statistics - {}", statistics.toString());
        return statistics;
    }

}

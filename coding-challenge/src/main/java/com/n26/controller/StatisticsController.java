package com.n26.controller;

import com.n26.model.StatisticsDto;
import com.n26.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {



    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public StatisticsDto getStatistics() {
        // O(1) since directly getting from service
        return statisticsService.getStatistics();
    }

}

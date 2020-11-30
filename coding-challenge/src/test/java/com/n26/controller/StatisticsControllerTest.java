package com.n26.controller;

import com.n26.model.StatisticsDto;
import com.n26.model.TransactionDto;
import com.n26.service.StatisticsService;
import com.n26.service.TransactionService;
import com.n26.utils.AppUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableScheduling
@EnableAsync
public class StatisticsControllerTest {


    @Autowired
    public TransactionService transactionService;

    @Autowired
    public StatisticsService statisticsService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void StatisticsResponse_ReturnOK() throws Exception {

        ResponseEntity<String> res_delete = restTemplate.exchange("/transactions", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, res_delete.getStatusCode());

        TransactionDto transaction = new TransactionDto();
        transaction.setAmount(12.5);
        transaction.setTimestamp(Instant.now());

        ResponseEntity<Object> trans_res = restTemplate.postForEntity("/transactions", transaction, Object.class);
        assertEquals(HttpStatus.CREATED, trans_res.getStatusCode());

        ResponseEntity<Object> stat_res = restTemplate.getForEntity("/statistics", Object.class);
        assertEquals(HttpStatus.OK, stat_res.getStatusCode());

        StatisticsDto statisticsDto = new StatisticsDto(AppUtility.ToBigDecimal(12.5), AppUtility.ToBigDecimal(12.5), AppUtility.ToBigDecimal(12.5), AppUtility.ToBigDecimal(12.5), 1);
        assertEquals(statisticsDto.toString(), Objects.requireNonNull(stat_res.getBody()).toString());

    }


    @Test
    public void BigDecimalRoundingWithoutAnyDecimalPlace_ReturnOK() throws Exception {

        ResponseEntity<String> res = restTemplate.exchange("/transactions", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());

        TransactionDto transaction = new TransactionDto();
        transaction.setAmount(12);
        transaction.setTimestamp(Instant.now());

        ResponseEntity<Object> trans_res = restTemplate.postForEntity("/transactions", transaction, Object.class);
        assertEquals(HttpStatus.CREATED, trans_res.getStatusCode());

        ResponseEntity<Object> stat_res = restTemplate.getForEntity("/statistics", Object.class);
        assertEquals(HttpStatus.OK, stat_res.getStatusCode());

        StatisticsDto statisticsDto = new StatisticsDto(AppUtility.ToBigDecimal(12), AppUtility.ToBigDecimal(12), AppUtility.ToBigDecimal(12), AppUtility.ToBigDecimal(12), 1);
        assertEquals(statisticsDto.toString(), Objects.requireNonNull(stat_res.getBody()).toString());

    }


    @Test
    public void BigDecimalRoundingWithManyDecimalPlaces_ReturnOK() throws Exception {

        ResponseEntity<String> res = restTemplate.exchange("/transactions", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());

        TransactionDto transaction = new TransactionDto();
        transaction.setAmount(11.999999999999);
        transaction.setTimestamp(Instant.now());

        ResponseEntity<Object> trans_res = restTemplate.postForEntity("/transactions", transaction, Object.class);
        assertEquals(HttpStatus.CREATED, trans_res.getStatusCode());

        ResponseEntity<Object> stat_res = restTemplate.getForEntity("/statistics", Object.class);
        assertEquals(HttpStatus.OK, stat_res.getStatusCode());

        StatisticsDto statisticsDto = new StatisticsDto(AppUtility.ToBigDecimal(12), AppUtility.ToBigDecimal(12), AppUtility.ToBigDecimal(12), AppUtility.ToBigDecimal(12), 1);
        assertEquals(statisticsDto.toString(), Objects.requireNonNull(stat_res.getBody()).toString());

    }


    @Test
    public void DiscardOlderTransactions_ReturnOK() throws Exception {

        ResponseEntity<String> res = restTemplate.exchange("/transactions", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());

        TransactionDto transaction = new TransactionDto();
        transaction.setAmount(11.999999999999);
        transaction.setTimestamp(Instant.now().minusSeconds(55));

        ResponseEntity<Object> trans_res = restTemplate.postForEntity("/transactions", transaction, Object.class);
        assertEquals(HttpStatus.CREATED, trans_res.getStatusCode());

        ResponseEntity<Object> stat_res = restTemplate.getForEntity("/statistics", Object.class);
        assertEquals(HttpStatus.OK, stat_res.getStatusCode());

        StatisticsDto statisticsDto = new StatisticsDto(AppUtility.ToBigDecimal(12), AppUtility.ToBigDecimal(12), AppUtility.ToBigDecimal(12), AppUtility.ToBigDecimal(12), 1);
        assertEquals(statisticsDto.toString(), Objects.requireNonNull(stat_res.getBody()).toString());

/*
        //TODO
        Thread.sleep(10000);

        ResponseEntity<Object> stat_res_after_delete = restTemplate.getForEntity("/statistics", Object.class);
        assertEquals(HttpStatus.OK, stat_res_after_delete.getStatusCode());

        StatisticsDto statisticsDto_AfterDelete = new StatisticsDto(AppUtility.ToBigDecimal(0), AppUtility.ToBigDecimal(0), AppUtility.ToBigDecimal(0), AppUtility.ToBigDecimal(0), 0);
        assertEquals(statisticsDto_AfterDelete.toString(), Objects.requireNonNull(stat_res_after_delete.getBody()).toString());*/

    }
}

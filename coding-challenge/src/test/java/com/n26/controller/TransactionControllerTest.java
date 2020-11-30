package com.n26.controller;

import com.n26.model.TransactionDto;
import com.n26.service.StatisticsService;
import com.n26.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

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
    public void shouldReturn204ForOlderTransactions() throws Exception {

        ResponseEntity<String> res_delete = restTemplate.exchange("/transactions", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, res_delete.getStatusCode());

        TransactionDto transaction = new TransactionDto();
        transaction.setAmount(12.5);
        transaction.setTimestamp(Instant.now().minusSeconds(600));

        ResponseEntity<Object> res = restTemplate.postForEntity("/transactions", transaction, Object.class);
        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());
    }

    @Test
    public void InCaseOfSuccess_Return201() throws Exception {

        ResponseEntity<String> res_delete = restTemplate.exchange("/transactions", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, res_delete.getStatusCode());

        TransactionDto transaction = new TransactionDto();
        transaction.setAmount(12.5);
        transaction.setTimestamp(Instant.now().minusSeconds(5));

        ResponseEntity<Object> res = restTemplate.postForEntity("/transactions", transaction, Object.class);
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }

    @Test
    public void IfTransactionIsOlderThan60Secs_Return204() throws Exception {

        ResponseEntity<String> res_delete = restTemplate.exchange("/transactions", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, res_delete.getStatusCode());

        TransactionDto transaction = new TransactionDto();
        transaction.setAmount(12.5);
        transaction.setTimestamp(Instant.now().minusSeconds(100));

        ResponseEntity<Object> res = restTemplate.postForEntity("/transactions", transaction, Object.class);
        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());

    }

    @Test
    public void IfTransactionDateIsInTheFuture_Return422() throws Exception {

        ResponseEntity<String> res_delete = restTemplate.exchange("/transactions", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, res_delete.getStatusCode());

        TransactionDto transaction = new TransactionDto();
        transaction.setAmount(12.5);
        transaction.setTimestamp(Instant.now().plusSeconds(100));

        ResponseEntity<Object> res = restTemplate.postForEntity("/transactions", transaction, Object.class);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, res.getStatusCode());
    }


    @Test
    public void DeleteAllTransactions_Return204() throws Exception {

        ResponseEntity<String> res = restTemplate.exchange("/transactions", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());

    }
}

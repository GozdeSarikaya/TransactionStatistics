package com.n26.controller;

import com.n26.exception.CustomExceptionHandler;
import com.n26.model.TransactionDto;
import com.n26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

import static com.n26.service.TransactionService.TIME_LIMIT;
import static org.springframework.http.HttpStatus.*;

@Controller
@Validated
public class TransactionController {


    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "/transactions", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> submitTransaction(/*@Valid @NotNull*/ @RequestBody TransactionDto transaction) {

        try {
            long currenttime = Instant.now().atZone(ZoneId.systemDefault()).toEpochSecond();
            long timediff = currenttime - transaction.getTimestamp().toEpochMilli() / 1000;
            if (timediff < 0)
                return new ResponseEntity<>(UNPROCESSABLE_ENTITY);

            if (timediff >= TIME_LIMIT) {
                // Assume that we are to save a transaction only if it happened within the last minute
                return new ResponseEntity<>(NO_CONTENT);  // return empty body with 204 status
            } else {
                transactionService.addTransaction(transaction);
                return new ResponseEntity<>(CREATED); // return empty body with 201 status
            }

        } catch (Exception e) {
            return new ResponseEntity<>(NO_CONTENT);
        }
    }

    @DeleteMapping(path = "/transactions")
    public ResponseEntity<String> deleteAllTransactions() {

        try {
            transactionService.deleteAllTransactions();
        } catch (Exception e) {
            return ResponseEntity.status(NO_CONTENT).build();
        }

        return ResponseEntity.status(NO_CONTENT).build();
    }
/*
    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public ResponseEntity getStatistics(){
        long current = Instant.now().toEpochMilli();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity addStatistics(@RequestBody StatisticsRequest request){
        long current = Instant.now().toEpochMilli();
        boolean added = statisticsService.addStatistics(request, current);
        if(added) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }


 */
}

package com.n26.controller;

import com.n26.exception.TransactionExpiredException;
import com.n26.exception.TransactionOutOfFutureException;
import com.n26.model.TransactionDto;
import com.n26.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.HttpStatus.*;

@Controller
@Validated
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "/transactions", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> submitTransaction(/*@Valid @NotNull*/ @RequestBody TransactionDto transaction) {

        try {
            logger.debug("Transaction processing - {}", transaction.toString());
            transactionService.addTransaction(transaction);
            logger.debug("Transaction is successfully created - {}", transaction.toString());
        } catch (TransactionExpiredException e) {
            logger.trace("Transaction could not be created - {}", e.getMessage());
            return new ResponseEntity<>(NO_CONTENT); // 204 – if the transaction is older than 60 seconds
        } catch (TransactionOutOfFutureException e) {
            logger.trace("Transaction could not be created - {}", e.getMessage());
            return new ResponseEntity<>(UNPROCESSABLE_ENTITY); // 422 – if the transaction date is in the future
        }
        return new ResponseEntity<>(CREATED); // 201 – in case of success
    }

    @DeleteMapping(path = "/transactions")
    public ResponseEntity<String> deleteAllTransactions() {

        try {
            logger.debug("All transactions will be deleted");
            transactionService.deleteAllTransactions();
            logger.debug("All transactions are successfully deleted");
        } catch (Exception e) {
            logger.trace("All transactions could not be deleted - {}", e.getMessage());
            return ResponseEntity.status(NO_CONTENT).build();
        }
        return ResponseEntity.status(NO_CONTENT).build();
    }
}

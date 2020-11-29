package com.n26.controller;

import com.n26.exception.TransactionExpiredException;
import com.n26.exception.TransactionOutOfFutureException;
import com.n26.model.TransactionDto;
import com.n26.service.TransactionService;
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

    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "/transactions", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> submitTransaction(/*@Valid @NotNull*/ @RequestBody TransactionDto transaction) {

        try {
            transactionService.addTransaction(transaction);
        } catch (TransactionExpiredException e) {
            return new ResponseEntity<>(NO_CONTENT); // 204 – if the transaction is older than 60 seconds
        } catch (TransactionOutOfFutureException e) {
            return new ResponseEntity<>(UNPROCESSABLE_ENTITY); // 422 – if the transaction date is in the future
        }
        return new ResponseEntity<>(CREATED); // 201 – in case of success
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
}

package com.dp.paymentservice.controller;

import com.braintreegateway.Transaction;
import com.dp.paymentservice.model.transaction.TransactionAddRequest;
import com.dp.paymentservice.model.transaction.UserTransactionRequest;
import com.dp.paymentservice.model.transaction.TransactionWithdrawRequest;
import com.dp.paymentservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final RestClient restClient;
    private final TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<?> addTransactionHandler(@RequestBody TransactionAddRequest transactionRequest) {
        Optional<Transaction> transaction = Optional.ofNullable(transactionService.completeAddTransaction(transactionRequest));
        if(transaction.isPresent()) {

            // todo call user service to add balance

            return ResponseEntity.ok(transaction);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawTransactionHandler(@RequestBody TransactionWithdrawRequest transactionRequest) {
        transactionService.completeWithdrawalTransaction(transactionRequest);

        // todo call user service to reduce balance

        return ResponseEntity.ok().build();
    }

}

package com.dp.paymentservice.controller;

import com.braintreegateway.Transaction;
import com.dp.paymentservice.model.UserBalanceUpdateRequest;
import com.dp.paymentservice.model.transaction.TransactionAddRequest;
import com.dp.paymentservice.model.transaction.UserTransactionRequest;
import com.dp.paymentservice.model.transaction.TransactionWithdrawRequest;
import com.dp.paymentservice.service.TransactionService;
import com.dp.paymentservice.validator.TransactionValidator;
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
    private final TransactionValidator transactionValidator;

    @PostMapping("/add")
    public ResponseEntity<?> addTransactionHandler(@RequestBody TransactionAddRequest transactionRequest) {
        if(!transactionValidator.userExists(Long.valueOf(transactionRequest.userId()))) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Transaction> transaction = Optional.ofNullable(transactionService.completeAddTransaction(transactionRequest));
        if(transaction.isPresent()) {

            // todo call user service to add balance
            restClient.put()
                    .uri(builder -> builder
                            .pathSegment(
                                    "/api/user/",
                                    transactionRequest.userId(),
                                    "/balance"
                            )
                            .build()
                    )
                    .body(new UserBalanceUpdateRequest(
                            Long.valueOf(transactionRequest.userId()),
                            transactionRequest.amount(),
                            UserBalanceUpdateRequest.UpdateType.ADD
                    ))
                    .retrieve()
                    .toBodilessEntity();

            return ResponseEntity.ok(transaction);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawTransactionHandler(@RequestBody TransactionWithdrawRequest transactionRequest) {
        if(!transactionValidator.userExists(transactionRequest.userId())) {
            return ResponseEntity.badRequest().build();
        }

        transactionService.completeWithdrawalTransaction(transactionRequest);

        // todo call user service to reduce balance
        restClient.put()
                .uri(builder -> builder
                        .pathSegment(
                                "/api/user/",
                                String.valueOf(transactionRequest.userId()),
                                "/balance"
                        )
                        .build()
                )
                .body(new UserBalanceUpdateRequest(
                        transactionRequest.userId(),
                        transactionRequest.amount(),
                        UserBalanceUpdateRequest.UpdateType.SUBTRACT
                ))
                .retrieve()
                .toBodilessEntity();


        return ResponseEntity.ok().build();
    }

}

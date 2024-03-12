package com.dp.paymentservice.service;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.dp.paymentservice.model.transaction.TransactionAddRequest;
import com.dp.paymentservice.model.transaction.TransactionWithdrawRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final BraintreeGateway gateway;

    private final CustomerService customerService;

    @Override
    public Transaction completeWithdrawalTransaction(TransactionWithdrawRequest transactionRequest) {
        return null;
    }

    @Override
    public Transaction completeAddTransaction(TransactionAddRequest transactionRequest) {
        TransactionRequest request = new TransactionRequest()
                .amount(BigDecimal.valueOf(transactionRequest.amount()));
        if(transactionRequest.useDefaultMethod()) {
            String customerId = customerService.getCustomerFromUserId(transactionRequest.userId()).getId();
            request.customerId(customerId);
        }
        else {
            request.paymentMethodNonce(transactionRequest.paymentMethodNonce());
        }
        request.options().submitForSettlement(true).done();
        Result<Transaction> result = gateway.transaction().sale(request);

        if(result.isSuccess()) {
            log.info("completed transaction");
            return result.getTarget();
        }
        else {
            log.error("could not complete transaction: {}", result.getErrors().toString());
            return null;
        }
    }
}

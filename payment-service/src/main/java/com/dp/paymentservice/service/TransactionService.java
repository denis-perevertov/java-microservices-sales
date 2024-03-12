package com.dp.paymentservice.service;

import com.braintreegateway.Transaction;
import com.dp.paymentservice.model.transaction.TransactionAddRequest;
import com.dp.paymentservice.model.transaction.TransactionWithdrawRequest;
import com.dp.paymentservice.model.transaction.UserTransactionRequest;

public interface TransactionService {

//    Transaction completeTransaction(UserTransactionRequest request);

    Transaction completeWithdrawalTransaction(TransactionWithdrawRequest request);
    Transaction completeAddTransaction(TransactionAddRequest request);

}

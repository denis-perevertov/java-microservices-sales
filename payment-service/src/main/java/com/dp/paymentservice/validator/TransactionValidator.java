package com.dp.paymentservice.validator;

import com.dp.paymentservice.model.transaction.TransactionWithdrawRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class TransactionValidator {

    public void validateWithdraw(TransactionWithdrawRequest request, Errors e) {
        Long customerId = request.customerId();
        double amount = request.amount();
    }
}

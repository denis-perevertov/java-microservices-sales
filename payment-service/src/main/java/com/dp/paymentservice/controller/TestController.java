package com.dp.paymentservice.controller;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestController {

    private final BraintreeGateway gateway;

    @GetMapping
    public String test() {
        return "test";
    }

    @PostMapping("/test-payment")
    public ResponseEntity<?> testPayment(@RequestParam(name = "payment_method_nonce") String nonce) {
        log.info("nonce: {}", nonce);
        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal("1090.00"))
                .paymentMethodNonce(nonce)
                .options()
                .submitForSettlement(true)
                .done();
        Result<Transaction> result = gateway.transaction().sale(request);
        log.info("result: {}", result);
        if(result.isSuccess()) {
            log.info("SUCCESS");
        }
        else {
            log.error("FAIL");
            log.info(result.getErrors().toString());
        }
        return ResponseEntity.ok().build();
    }
}

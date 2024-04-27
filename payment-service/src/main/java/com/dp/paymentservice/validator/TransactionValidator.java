package com.dp.paymentservice.validator;

import com.dp.paymentservice.model.transaction.TransactionWithdrawRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionValidator {

    private final RestClient restClient;

    public void validateWithdraw(TransactionWithdrawRequest request, Errors e) {
        Long userId = request.userId();
        BigDecimal amount = request.amount();
    }

    public boolean userExists(Long userId) {
        if(userId == null || userId < 0) return false;
        return restClient.get()
                .exchange((req, res) -> {
                    if(res.getStatusCode().is2xxSuccessful()) {
                        return true;
                    }
                    else if(res.getStatusCode().is4xxClientError()) {
                        return false;
                    }
                    else {
                        log.error("error with request, status: {}", res.getStatusCode());
                        log.error("error: {}", res.getBody());
                        return false;
                    }
                });
    }
}

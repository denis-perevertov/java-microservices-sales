package com.dp.productservice.controller;

import com.dp.productservice.model.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final WebClient.Builder webClientBuilder;

    @GetMapping("/hello")
    public ResponseEntity<?> test(@RequestParam String name) {
        Test test = webClientBuilder.build().get()
                .uri("http://payment-service/hello?name=WEBCLIENT_TEST")
                .retrieve()
                .bodyToMono(Test.class)
                .block();
        return ResponseEntity.ok(test);
    }
}

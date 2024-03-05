package com.dp.paymentservice.controller;

import com.dp.paymentservice.model.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<?> test(@RequestParam String name) {
        return ResponseEntity.ok(new Test("Hello, " + name));
    }
}

package com.dp.deliveryservice.controller;

import com.dp.deliveryservice.model.CalculateCostRequest;
import com.dp.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final RestClient restClient;

    @PostMapping("/calculate-cost")
    public ResponseEntity<?> calculateDeliveryCost(@RequestBody CalculateCostRequest request) {
        return ResponseEntity.ok(deliveryService.calculateDeliveryCost(request));
    }

}

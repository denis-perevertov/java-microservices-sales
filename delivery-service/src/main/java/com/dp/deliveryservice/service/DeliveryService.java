package com.dp.deliveryservice.service;

import com.dp.deliveryservice.model.CalculateCostRequest;
import com.dp.deliveryservice.model.CalculateCostResponse;

public interface DeliveryService {

    CalculateCostResponse calculateCost(CalculateCostRequest request);

}

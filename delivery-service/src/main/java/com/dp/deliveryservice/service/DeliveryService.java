package com.dp.deliveryservice.service;

import com.dp.deliveryservice.model.CalculateCostRequest;
import com.dp.deliveryservice.model.CalculateCostResponse;
import com.dp.deliveryservice.persistence.entity.Receiver;
import com.dp.deliveryservice.persistence.entity.ReceiverAddress;
import com.dp.deliveryservice.persistence.entity.WarehouseAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DeliveryService {

    CalculateCostResponse calculateDeliveryCost(CalculateCostRequest request);


}

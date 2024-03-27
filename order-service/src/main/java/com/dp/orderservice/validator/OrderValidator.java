package com.dp.orderservice.validator;

import com.dp.orderservice.model.OrderCreateRequest;
import com.dp.orderservice.persistence.entity.OrderStatus;
import com.dp.orderservice.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.dp.utils.EnumUtils.getEnum;

@Component
@RequiredArgsConstructor
public class OrderValidator {
    private final OrderRepository orderRepository;

    public boolean orderExists(Long id) { return orderRepository.existsById(id); }

    public boolean createRequestValid(OrderCreateRequest request, Errors e) {
        // todo
        return true;
    }

    public boolean orderStatusIsValid(String status) {
        return getEnum(status, OrderStatus.class) != null;
    }
}

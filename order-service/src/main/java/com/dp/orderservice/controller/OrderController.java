package com.dp.orderservice.controller;

import com.dp.orderservice.mapper.OrderMapper;
import com.dp.orderservice.model.OrderCreateRequest;
import com.dp.orderservice.model.OrderPageRequest;
import com.dp.orderservice.model.OrderStatusUpdateRequest;
import com.dp.orderservice.persistence.entity.Order;
import com.dp.orderservice.persistence.specification.OrderListSpecification;
import com.dp.orderservice.service.OrderService;
import com.dp.orderservice.validator.OrderValidator;
import com.dp.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderValidator orderValidator;
    private final OrderMapper orderMapper;

    @GetMapping({"", "/"})
    public ResponseEntity<?> getOrders(OrderPageRequest request) {
        Page<Order> page = orderService.getOrdersPage(new OrderListSpecification(request), PageRequest.of(request.page(), request.size()));
        return ResponseEntity.ok(page.map(orderMapper::orderToDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        if(!orderValidator.orderExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        Order order = orderService.getOrder(id);
        return ResponseEntity.ok(orderMapper.orderToDTO(order));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest request,
                                         BindingResult bindingResult) {
        if(!orderValidator.createRequestValid(request, bindingResult)) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        Order order = orderService.createNewOrder(request);
        return ResponseEntity.ok(orderMapper.orderToDTO(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editOrder(@PathVariable Long id,
                                       @RequestBody OrderCreateRequest request,
                                       BindingResult bindingResult) {
        if(!orderValidator.orderExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        if(!orderValidator.createRequestValid(request, bindingResult)) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestBody OrderStatusUpdateRequest request) {
        if(
                !orderValidator.orderExists(request.id())
                || !orderValidator.orderStatusIsValid(request.status())
        ) {
            return ResponseEntity.badRequest().build();
        }
        orderService.updateOrderStatus(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        if(!orderValidator.orderExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}

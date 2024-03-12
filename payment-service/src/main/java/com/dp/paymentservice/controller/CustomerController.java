package com.dp.paymentservice.controller;

import com.braintreegateway.*;
import com.dp.paymentservice.model.ClientTokenResponse;
import com.dp.paymentservice.model.CustomerCreateRequest;
import com.dp.paymentservice.model.card.CustomerCreditCardCreateRequest;
import com.dp.paymentservice.model.card.CustomerCreditCardDeleteRequest;
import com.dp.paymentservice.model.card.CustomerCreditCardUpdateRequest;
import com.dp.paymentservice.persistence.entity.UserPaymentData;
import com.dp.paymentservice.persistence.repository.UserPaymentDataRepository;
import com.dp.paymentservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getCustomer(@RequestParam String userId) {
        return ResponseEntity.ok(customerService.getCustomerFromUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerCreateRequest customer,
                                            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            log.error("errors");
            log.error(bindingResult.getFieldErrors().toString());
            return ResponseEntity.badRequest().build();
        }

        Optional<Customer> createdCustomer = Optional.ofNullable(customerService.createCustomer(customer));
        if(createdCustomer.isPresent()) {
            return ResponseEntity.ok(createdCustomer.get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCustomer(@RequestParam String userId) {
        customerService.deleteCustomerByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cards")
    public ResponseEntity<?> getCustomerCreditCards(@RequestParam String userId) {
        return ResponseEntity.ok(customerService.getCustomerCreditCardsFromUserId(userId));
    }

    @PostMapping("/cards")
    public ResponseEntity<?> createCustomerCreditCardData(@RequestBody CustomerCreditCardCreateRequest createRequest) {
        Optional<PaymentMethod> createdData = Optional.ofNullable(customerService.createCustomerCreditCard(createRequest));
        if(createdData.isPresent()) {
            return ResponseEntity.ok(createdData.get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/cards")
    public ResponseEntity<?> updateCustomerCreditCardData(@RequestBody CustomerCreditCardUpdateRequest updateRequest) {
        Optional<PaymentMethod> updatedData = Optional.ofNullable(customerService.updateCustomerCreditCard(updateRequest));
        if(updatedData.isPresent()) {
            return ResponseEntity.ok(updatedData.get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/cards")
    public ResponseEntity<?> deleteCustomerCreditCardData(@RequestBody CustomerCreditCardDeleteRequest deleteRequest) {
        customerService.deleteCustomerCreditCard(deleteRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client-token")
    public ResponseEntity<?> getClientToken(@RequestParam(required = false) String customerId) {
        return ResponseEntity.ok(
                new ClientTokenResponse(customerService.getCustomerClientToken(customerId))
        );
    }
}

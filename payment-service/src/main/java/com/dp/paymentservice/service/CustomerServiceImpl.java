package com.dp.paymentservice.service;

import com.braintreegateway.*;
import com.dp.paymentservice.model.CustomerCreateRequest;
import com.dp.paymentservice.model.card.CustomerCreditCardCreateRequest;
import com.dp.paymentservice.model.card.CustomerCreditCardDeleteRequest;
import com.dp.paymentservice.model.card.CustomerCreditCardUpdateRequest;
import com.dp.paymentservice.persistence.entity.UserPaymentData;
import com.dp.paymentservice.persistence.repository.UserPaymentDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final String DEFAULT_ID = "14812945583";

    private final BraintreeGateway gateway;
    private final UserPaymentDataRepository userPaymentDataRepository;

    @Override
    public String getCustomerClientToken(String customerId) {
        ClientTokenRequest request = new ClientTokenRequest()
                .customerId(Optional.ofNullable(customerId).orElse(DEFAULT_ID));
        String clientToken = gateway.clientToken().generate(request);
        log.info("client token: {}", clientToken);
        return clientToken;
    }

    @Override
    public Customer getCustomerFromUserId(String userId) {
        String paymentProfileId = userPaymentDataRepository.findByUserId(userId).orElseThrow().getPaymentProfileId();
        log.info("found payment profile id: {}", paymentProfileId);
        Customer customer = gateway.customer().find(paymentProfileId);
        log.info("customer: {}", customer);
        return customer;
    }

    @Override
    public Customer createCustomer(CustomerCreateRequest createRequest) {
        CustomerRequest request = new CustomerRequest()
                .firstName(createRequest.firstName())
                .lastName(createRequest.lastName())
                .company(createRequest.company())
                .email(createRequest.email())
                .fax(createRequest.fax())
                .phone(createRequest.phone())
                .website(createRequest.website())
                .paymentMethodNonce(createRequest.paymentMethodNonce());

        Result<Customer> result = gateway.customer().create(request);

        if(result.isSuccess()) {
            log.info("Created new Braintree customer, id: {}", result.getTarget().getId());
            UserPaymentData paymentData = userPaymentDataRepository.save(new UserPaymentData(createRequest.userId(), result.getTarget().getId()));
            log.info("created payment data: {}" , paymentData);
            return result.getTarget();
        }
        else {
            log.error("Could not create new customer: {}", result.getErrors().toString());
            return null;
        }
    }

    @Override
    public void deleteCustomerByUserId(String userId) {
        String customerId = getCustomerFromUserId(userId).getId();
        Result<Customer> result = gateway.customer().delete(customerId);
        if(result.isSuccess()) {
            log.info("customer deleted: {}", customerId);
        }
        else {
            log.error("could not delete customer: {}", customerId);
            log.error("errors: {}", result.getErrors().toString());
        }
    }

    @Override
    public List<CreditCard> getCustomerCreditCardsFromUserId(String userId) {
        return getCustomerFromUserId(userId).getCreditCards();
    }

    @Override
    public PaymentMethod createCustomerCreditCard(CustomerCreditCardCreateRequest createRequest) {
        PaymentMethodRequest request = new PaymentMethodRequest()
                .customerId(getCustomerFromUserId(createRequest.userId()).getId())
                .paymentMethodNonce(createRequest.paymentMethodNonce())
                .options()
                .makeDefault(createRequest.setAsDefault())
                .done();

        Result<? extends PaymentMethod> result = gateway.paymentMethod().create(request);

        if(result.isSuccess()) {
            log.info("created card data: {}", result.getTarget());
            return result.getTarget();
        }
        else {
            log.error("Could not create card data: {}", result.getErrors().toString());
            return null;
        }
    }

    @Override
    public PaymentMethod updateCustomerCreditCard(CustomerCreditCardUpdateRequest updateRequest) {
        PaymentMethodRequest request = new PaymentMethodRequest()
                .options()
                .makeDefault(updateRequest.isDefault())
                .done();

        Result<? extends PaymentMethod> result = gateway.paymentMethod().update(updateRequest.token(), request);

        if(result.isSuccess()) {
            log.info("updated card data: {}", result.getTarget());
            return result.getTarget();
        }
        else {
            log.error("Could not update card data: {}", result.getErrors().toString());
            return null;
        }
    }

    @Override
    public void deleteCustomerCreditCard(CustomerCreditCardDeleteRequest request) {
        Result<? extends PaymentMethod> result = gateway.paymentMethod().delete(request.token());
        if(result.isSuccess()) {
            log.info("customer deleted");
        }
        else {
            log.error("could not delete credit card");
            log.error("errors: {}", result.getErrors().toString());
        }
    }

}

package com.dp.paymentservice.service;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.PaymentMethod;
import com.dp.paymentservice.model.CustomerCreateRequest;
import com.dp.paymentservice.model.card.CustomerCreditCardCreateRequest;
import com.dp.paymentservice.model.card.CustomerCreditCardDeleteRequest;
import com.dp.paymentservice.model.card.CustomerCreditCardUpdateRequest;

import java.util.List;

public interface CustomerService {

    String getCustomerClientToken(String customerId);
    Customer getCustomerFromUserId(String userId);
    Customer createCustomer(CustomerCreateRequest createRequest);
    void deleteCustomerByUserId(String userId);

    List<CreditCard> getCustomerCreditCardsFromUserId(String userId);
    PaymentMethod createCustomerCreditCard(CustomerCreditCardCreateRequest request);
    PaymentMethod updateCustomerCreditCard(CustomerCreditCardUpdateRequest request);
    void deleteCustomerCreditCard(CustomerCreditCardDeleteRequest request);
}

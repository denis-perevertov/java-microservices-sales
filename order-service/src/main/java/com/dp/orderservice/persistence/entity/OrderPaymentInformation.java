package com.dp.orderservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "order_payment_info")
@Accessors(chain = true)
public class OrderPaymentInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    double productPrice;
    double commissionFee;
    double insuranceFee;
    double processingFee;
    double deliveryFee;
    double approximateWeight;
    double realWeight;

    double totalPrice;
}

package com.dp.orderservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_payment_info")
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

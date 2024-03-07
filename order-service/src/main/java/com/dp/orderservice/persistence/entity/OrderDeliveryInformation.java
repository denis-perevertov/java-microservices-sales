package com.dp.orderservice.persistence.entity;

import com.dp.deliveryservice.persistence.DeliveryType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_delivery_info")
public class OrderDeliveryInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    DeliveryType deliveryType;

    @Column(length = 100)
    String trackingNumber;

    LocalDateTime sentAt;
    LocalDateTime deliveredAt;

    // todo add delivery address ?
    // todo add warehouse address ?
}

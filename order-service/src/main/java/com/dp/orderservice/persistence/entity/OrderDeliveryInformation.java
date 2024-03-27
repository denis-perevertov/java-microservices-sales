package com.dp.orderservice.persistence.entity;

import com.dp.deliveryservice.persistence.DeliveryMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "order_delivery_info")
@Accessors(chain = true)
public class OrderDeliveryInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    DeliveryMethod deliveryMethod;

    @Column(length = 100)
    String trackingNumber;

    ZonedDateTime sentAt;
    ZonedDateTime deliveredAt;

    Long receiverAddressId;
    Long warehouseAddressId;

}

package com.dp.orderservice.persistence.entity;

import com.dp.productservice.persistence.entity.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long productId;

    private int quantity;

    @ElementCollection
    private List<ExtraService> extraServices;

    // usa ?
    @Column(length = 100)
    private String trackingNumber;

    @Column(length = 500)
    private String comment;
}

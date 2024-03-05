package com.dp.productservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private double priceUSA;
    private double priceUKR;

    @Column(nullable = false)
    private String link;

    private String img;


}

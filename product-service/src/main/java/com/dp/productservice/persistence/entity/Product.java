package com.dp.productservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double priceUSA;
    @Column(nullable = false)
    private Double priceUKR;

    @Column(nullable = false)
    private String link;

    private String img;

    @ManyToOne
    private Category category;


}

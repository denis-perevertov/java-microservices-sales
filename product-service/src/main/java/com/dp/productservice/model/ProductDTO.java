package com.dp.productservice.model;

public record ProductDTO (
        String name,
        double priceUSA,
        double priceUKR,
        String link,
        String img,
        CategoryDTO category
) {
}

package com.dp.productservice.model;

public record ProductPageRequest(
        String name,
        Double priceFrom,
        Double priceTo,
        Long categoryId,
        Integer page,
        Integer size
) {
    public ProductPageRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}

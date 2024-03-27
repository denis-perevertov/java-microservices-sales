package com.dp.productservice.model;

public record ProductPageRequest(
        Integer page,
        Integer size
) {
    public ProductPageRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}

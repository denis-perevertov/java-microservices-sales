package com.dp.productservice.model;

public record ProductListRequest(
        Integer page,
        Integer size
) {
    public ProductListRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}

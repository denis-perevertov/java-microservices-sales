package com.dp.productservice.model;

import jakarta.validation.constraints.Max;

public record CategoryListRequest(
        Integer page,
        Integer size,
        @Max(255)
        String query
) {
    public CategoryListRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}

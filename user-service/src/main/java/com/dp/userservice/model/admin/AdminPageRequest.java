package com.dp.userservice.model.admin;

public record AdminPageRequest(
        Long id,
        String email,
        String name,
        Integer page,
        Integer size
) {
    public AdminPageRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}

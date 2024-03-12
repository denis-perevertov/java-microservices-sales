package com.dp.userservice.model.admin;

import jakarta.validation.constraints.Email;

public record AdminSaveRequest(
        Long id,
        @Email
        String email,
        String firstName,
        String lastName
) {
}

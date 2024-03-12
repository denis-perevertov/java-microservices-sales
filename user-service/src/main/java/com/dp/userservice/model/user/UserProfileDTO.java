package com.dp.userservice.model.user;

import java.time.ZonedDateTime;

public record UserProfileDTO (
        String firstName,
        String lastName,
        ZonedDateTime birthdate,
        String avatar,
        String email,
        String phone,
        String gender
) {
}

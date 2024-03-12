package com.dp.userservice.model.user;

import java.time.ZonedDateTime;
import java.util.List;

public record UserSaveRequest(
        Long id,
        String firstName,
        String lastName,
        ZonedDateTime birthdate,
        String email,
        String phone,
        String gender,
        List<Long> favorites
) {
}

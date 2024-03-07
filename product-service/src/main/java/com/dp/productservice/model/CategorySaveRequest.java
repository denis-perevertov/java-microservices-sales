package com.dp.productservice.model;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CategorySaveRequest(
        Long id,
        @NotBlank
        @Length(max = 255)
        String name,
        List<Long> productIds
) {
}

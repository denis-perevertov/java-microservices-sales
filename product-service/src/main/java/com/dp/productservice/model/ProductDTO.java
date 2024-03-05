package com.dp.productservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ProductDTO (
        @NotBlank
        @Length(max = 255)
        String name,
        @Min(1)
        double priceUSA,
        @Min(1)
        double priceUKR,
        @NotBlank
        @Length(max = 255)
        String link,
        @Length(max = 255)
        String img
) {
}

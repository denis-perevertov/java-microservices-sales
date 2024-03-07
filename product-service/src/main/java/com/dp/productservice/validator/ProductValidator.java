package com.dp.productservice.validator;

import com.dp.productservice.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    public boolean productExists(Long id) {
        return productRepository.existsById(id);
    }
}

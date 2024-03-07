package com.dp.productservice.validator;

import com.dp.productservice.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidator {
    private final CategoryRepository categoryRepository;

    public boolean categoryExists(Long id){
        return categoryRepository.existsById(id);
    }
}

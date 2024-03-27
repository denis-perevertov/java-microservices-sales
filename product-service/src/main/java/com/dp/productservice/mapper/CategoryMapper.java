package com.dp.productservice.mapper;

import com.dp.productservice.model.CategoryDTO;
import com.dp.productservice.model.CategorySaveRequest;
import com.dp.productservice.persistence.entity.Category;
import com.dp.productservice.persistence.repository.CategoryRepository;
import com.dp.productservice.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName()
        );
    }

    public Category toCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.name());
        return category;
    }

    public Category toCategory(CategoryDTO dto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(dto.name());
        return category;
    }

    public Category createRequestToCategory(CategorySaveRequest request) {
        Category category = new Category();
        category.setName(request.name());
        request.productIds().forEach(id -> productRepository.findById(id).ifPresent(product -> {
            product.setCategory(category);
            category.getProducts().add(product);
        }));

        return category;
    }

}

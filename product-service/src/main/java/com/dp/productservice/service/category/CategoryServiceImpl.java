package com.dp.productservice.service.category;

import com.dp.productservice.model.CategorySaveRequest;
import com.dp.productservice.persistence.entity.Category;
import com.dp.productservice.persistence.entity.Product;
import com.dp.productservice.persistence.repository.CategoryRepository;
import com.dp.productservice.persistence.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        log.info("getting all categories");
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> getCategoriesPage(Pageable pageable, String query) {
        log.info("getting all categories by search query: {}", query);
//        return categoryRepository.findByNameLikeIgnoreCase(query, pageable);
        return (query == null || query.isEmpty())
                ? categoryRepository.findAll(pageable)
                : categoryRepository.findByNameContainingIgnoreCase(query, pageable);
    }

    @Override
    public Category getCategory(Long id) {
        log.info("getting category with ID: {}", id);
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found!"));
    }

    @Override
    public Category saveCategory(Category category) {
        category = categoryRepository.save(category);
        log.info("category saved (ID: {})", category.getId());
        return category;
    }

    @Override
    @Transactional
    public Category saveCategory(CategorySaveRequest request) {
        Category category = request.id() != null
                ? categoryRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException("Category not found!"))
                : new Category();
        category.setId(request.id());
        category.setName(request.name());
        category = categoryRepository.save(category);
        log.info("category saved (ID: {}), ", category.getId());
        category.getProducts().forEach(p -> p.setCategory(null));
        category.getProducts().clear();
        for(Long id : request.productIds()) {
            Optional<Product> opt = productRepository.findById(id);
            if(opt.isPresent()) {
                Product product = opt.get();
                product.setCategory(category);
                category.getProducts().add(product);
            }
        }
        log.info("updated product list");
        category = categoryRepository.save(category);
        log.info("category saved (ID: {})", category.getId());
        return category;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        log.info("category deleted (ID: {})", id);
    }
}

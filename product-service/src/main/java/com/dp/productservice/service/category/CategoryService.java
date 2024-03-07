package com.dp.productservice.service.category;

import com.dp.productservice.model.CategorySaveRequest;
import com.dp.productservice.persistence.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    Page<Category> getCategoriesPage(Pageable pageable, String query);
    Category getCategory(Long id);
    Category saveCategory(Category category);
    Category saveCategory(CategorySaveRequest request);
    void deleteCategory(Long id);
}

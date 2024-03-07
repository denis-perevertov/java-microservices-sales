package com.dp.productservice.controller;

import com.dp.productservice.mapper.CategoryMapper;
import com.dp.productservice.model.CategorySaveRequest;
import com.dp.productservice.model.CategoryListRequest;
import com.dp.productservice.persistence.entity.Category;
import com.dp.productservice.service.category.CategoryService;
import com.dp.productservice.validator.CategoryValidator;
import com.dp.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper mapper;
    private final CategoryValidator validator;

    @GetMapping
    public ResponseEntity<?> getCategories(CategoryListRequest request) {
        Page<Category> categories = categoryService.getCategoriesPage(PageRequest.of(request.page(), request.size()), request.query());
        return ResponseEntity.ok(categories.map(mapper::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);
        return ResponseEntity.ok(mapper.toDTO(category));
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategorySaveRequest request,
                                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        Category category = categoryService.saveCategory(request);
        return ResponseEntity.status(201).body(mapper.toDTO(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCategory(@PathVariable Long id,
                                          @RequestBody @Valid CategorySaveRequest request,
                                          BindingResult bindingResult) {
        if(!validator.categoryExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        Category category = categoryService.saveCategory(request);
        return ResponseEntity.ok(mapper.toDTO(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        if(!validator.categoryExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }



}

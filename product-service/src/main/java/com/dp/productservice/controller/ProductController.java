package com.dp.productservice.controller;

import com.dp.productservice.mapper.ProductMapper;
import com.dp.productservice.model.OrderProductInfoRequest;
import com.dp.productservice.model.ProductPageRequest;
import com.dp.productservice.model.ProductSaveRequest;
import com.dp.productservice.persistence.entity.Product;
import com.dp.productservice.persistence.specification.ProductListSpecification;
import com.dp.productservice.service.product.ProductService;
import com.dp.productservice.validator.ProductValidator;
import com.dp.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper mapper;
    private final ProductValidator validator;


    @GetMapping("/test")
    public Object test() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt user = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user: {}", user.toString());
        log.info(user.getClaimAsMap("realm_access").toString());
        log.info(user.getClaimAsMap("realm_access").get("roles").toString());
        return "test";
    }

    @GetMapping
    public ResponseEntity<?> getProducts(ProductPageRequest request) {

        return ResponseEntity.ok(productService.getProductsPage(PageRequest.of(request.page(), request.size()), new ProductListSpecification(request))
                .stream()
                .map(mapper::toDTO)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        if(!validator.productExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mapper.toDTO(productService.getProduct(id)));
    }

    @PostMapping("/info")
    public ResponseEntity<?> getFullProductInfo(@RequestBody OrderProductInfoRequest request) {
        List<Product> products = productService.getProductsByIds(request.ids());
        return ResponseEntity.ok(products.stream().map(mapper::toDTO));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductSaveRequest request,
                                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        Product product = mapper.saveRequestToProduct(request);
        return ResponseEntity.status(201).body(productService.saveProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editProduct(@PathVariable Long id,
                                         @RequestBody @Valid ProductSaveRequest request,
                                         BindingResult bindingResult) {
        if(!validator.productExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        Product product = mapper.saveRequestToProduct(request, id);
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if(!validator.productExists(id)) {
            return ResponseEntity.badRequest().build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}

package com.dp.productservice.service.product;

import com.dp.productservice.model.ProductSaveRequest;
import com.dp.productservice.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    Page<Product> getProductsPage(Pageable pageable, Specification<Product> spec);
    Product getProduct(Long id);
    Product saveProduct(Product product);
    Product saveProduct(ProductSaveRequest request);
    void deleteProduct(Long id);

}

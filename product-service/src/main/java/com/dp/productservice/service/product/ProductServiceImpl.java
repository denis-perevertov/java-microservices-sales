package com.dp.productservice.service.product;

import com.dp.productservice.mapper.ProductMapper;
import com.dp.productservice.model.ProductSaveRequest;
import com.dp.productservice.persistence.entity.Product;
import com.dp.productservice.persistence.repository.CategoryRepository;
import com.dp.productservice.persistence.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts() {
        log.info("getting all products");
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByIds(List<Long> ids) {
        log.info("getting products from supplied id list");
        return productRepository.findByIdIn(ids);
    }

    @Override
    public Page<Product> getProductsPage(Pageable pageable, Specification<Product> spec) {
        log.info("getting page {} of products (size {})", pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findAll(spec, pageable);
    }

    @Override
    public Product getProduct(Long id) {
        log.info("getting product (ID: {})", id);
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found!"));
    }

    @Override
    public Product saveProduct(Product product) {
        product = productRepository.save(product);
        log.info("product saved (ID: {})", product);
        return product;
    }

    @Override
    public Product saveProduct(ProductSaveRequest request) {
        Product product = productRepository.save(productMapper.saveRequestToProduct(request));
        log.info("product saved (ID: {})", product.getId());
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        log.info("product deleted (ID: {})", id);
    }
}

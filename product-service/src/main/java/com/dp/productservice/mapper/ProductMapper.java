package com.dp.productservice.mapper;

import com.dp.productservice.model.ProductDTO;
import com.dp.productservice.model.ProductSaveRequest;
import com.dp.productservice.persistence.entity.Product;
import com.dp.productservice.persistence.repository.ProductRepository;
import com.dp.productservice.service.category.CategoryService;
import com.dp.productservice.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductService productService;

    public ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getPriceUSA(),
                product.getPriceUKR(),
                product.getLink(),
                product.getImg()
        );
    }

    public Product toProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPriceUKR(dto.priceUKR());
        product.setPriceUSA(dto.priceUSA());
        product.setLink(dto.link());
        product.setImg(dto.img());
        return product;
    }

    public Product toProduct(ProductDTO dto, Long originalProductID) {
        Product originalProduct = productRepository.findById(originalProductID).orElseThrow();
        originalProduct.setName(dto.name());
        originalProduct.setPriceUKR(dto.priceUKR());
        originalProduct.setPriceUSA(dto.priceUSA());
        originalProduct.setLink(dto.link());
        originalProduct.setImg(dto.img());
        return originalProduct;
    }

    public Product saveRequestToProduct(ProductSaveRequest request) {
        Product product = new Product();
        return mapProduct(product, request);
    }

    public Product saveRequestToProduct(ProductSaveRequest request, Long originalProductId) {
        Product product = productService.getProduct(originalProductId);
        return mapProduct(product, request);
    }

    private Product mapProduct(Product product,
                               ProductSaveRequest request) {
        product.setName(request.name())
                .setLink(request.link())
                .setImg(request.img())
                .setPriceUKR(request.priceUKR())
                .setPriceUSA(request.priceUSA());
        if(request.categoryId() != null && request.categoryId() >= 0) {
            product.setCategory(categoryService.getCategory(request.categoryId()));
        }
        return product;
    }

}

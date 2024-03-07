package com.dp.productservice.mapper;

import com.dp.productservice.model.ProductDTO;
import com.dp.productservice.persistence.entity.Product;
import com.dp.productservice.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductRepository productRepository;

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
}

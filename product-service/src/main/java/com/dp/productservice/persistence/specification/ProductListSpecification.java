package com.dp.productservice.persistence.specification;

import com.dp.productservice.model.ProductPageRequest;
import com.dp.productservice.persistence.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class ProductListSpecification implements Specification<Product> {

    private final ProductPageRequest request;

    @Override
    public Predicate toPredicate(@NonNull Root<Product> product,
                                 @NonNull CriteriaQuery<?> q,
                                 @NonNull CriteriaBuilder cb) {
        return null;
    }
}

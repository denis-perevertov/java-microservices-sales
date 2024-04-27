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

import java.util.ArrayList;
import java.util.List;

import static com.dp.utils.ValidationUtils.*;
import static com.dp.utils.ValidationUtils.*;

@RequiredArgsConstructor
public class ProductListSpecification implements Specification<Product> {

    private final ProductPageRequest request;

    @Override
    public Predicate toPredicate(@NonNull Root<Product> product,
                                 @NonNull CriteriaQuery<?> q,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(fieldIsNotEmpty(request.name())) {
            predicates.add(cb.like(product.get("name"), "%"+request.name()+"%"));
        }

        if(doubleValueNonNegative(request.priceFrom())) {
            if(doubleValueNonNegative(request.priceTo())) {
                predicates.add(cb.between(product.get("priceUSA"), request.priceFrom(), request.priceTo()));
            }
            else {
                predicates.add(cb.greaterThanOrEqualTo(product.get("priceUSA"), request.priceFrom()));
            }
        }
        else if(doubleValueNonNegative(request.priceTo())) {
            predicates.add(cb.lessThanOrEqualTo(product.get("priceUSA"), request.priceTo()));
        }

        if(fieldIsNotEmpty(request.categoryId())) {
            predicates.add(cb.equal(product.get("category").get("id"), request.categoryId()));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}

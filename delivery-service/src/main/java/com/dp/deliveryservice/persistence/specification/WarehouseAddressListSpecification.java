package com.dp.deliveryservice.persistence.specification;

import com.dp.deliveryservice.model.WarehouseAddressPageRequest;
import com.dp.deliveryservice.persistence.entity.WarehouseAddress;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static com.dp.utils.ValidationUtils.*;
import static com.dp.utils.EnumUtils.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WarehouseAddressListSpecification implements Specification<WarehouseAddress> {

    private final WarehouseAddressPageRequest request;

    @Override
    public Predicate toPredicate(@NonNull Root<WarehouseAddress> address,
                                 @NonNull CriteriaQuery<?> q,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(fieldIsNotEmpty(request.id())) {
            predicates.add(cb.and(cb.equal(address.get("id"), request.id())));
        }
        if(fieldIsNotEmpty(request.street())) {
            predicates.add(cb.and(cb.like(address.get("street"), "%"+request.street()+"%")));
        }
        if(fieldIsNotEmpty(request.city())) {
            predicates.add(cb.and(cb.like(address.get("city"), "%"+request.city()+"%")));
        }
        if(fieldIsNotEmpty(request.state())) {
            predicates.add(cb.and(cb.like(address.get("state"), "%"+request.state()+"%")));
        }
        if(fieldIsNotEmpty(request.zip())) {
            predicates.add(cb.and(cb.like(address.get("zip"), "%"+request.zip()+"%")));
        }
        if(fieldIsNotEmpty(request.phone())) {
            predicates.add(cb.and(cb.like(address.get("phone"), "%"+request.phone()+"%")));
        }
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}

package com.dp.orderservice.persistence.specification;

import com.dp.deliveryservice.persistence.DeliveryMethod;
import com.dp.orderservice.model.OrderPageRequest;
import com.dp.orderservice.persistence.entity.Order;
import com.dp.orderservice.persistence.entity.OrderStatus;
import com.dp.orderservice.persistence.entity.OrderType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static com.dp.utils.EnumUtils.*;
import static com.dp.utils.ValidationUtils.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderListSpecification implements Specification<Order> {

    private final OrderPageRequest r;

    @Override
    public Predicate toPredicate(@NonNull Root<Order> order,
                                 @NonNull CriteriaQuery<?> q,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(fieldIsNotEmpty(r.id())) {
            predicates.add(cb.equal(order.get("id"), r.id()));
        }
        if(fieldIsNotEmpty(r.orderNumber())) {
            predicates.add(cb.like(order.get("orderNumber"), "%"+r.orderNumber()+"%"));
        }
        if(fieldIsNotEmpty(r.userId())) {
            predicates.add(cb.equal(order.get("userId"), r.userId()));
        }

        if(doubleValueNonNegative(r.priceFrom())) {
            if(doubleValueNonNegative(r.priceTo())) {
                predicates.add(cb.between(order.get("paymentInformation").get("totalPrice"), r.priceFrom(), r.priceTo()));
            }
            else {
                predicates.add(cb.greaterThanOrEqualTo(order.get("paymentInformation").get("totalPrice"), r.priceFrom()));
            }
        }
        else if(doubleValueNonNegative(r.priceTo())) {
            predicates.add(cb.lessThanOrEqualTo(order.get("paymentInformation").get("totalPrice"), r.priceTo()));
        }

        DeliveryMethod method = getEnum(r.deliveryMethod(), DeliveryMethod.class);
        if(fieldIsNotEmpty(method)) {
            predicates.add(cb.equal(order.get("deliveryInformation").get("deliveryMethod"), method));
        }

        if(fieldIsNotEmpty(r.trackingNumber())) {
            predicates.add(cb.like(order.get("deliveryInformation").get("trackingNumber"), "%"+r.trackingNumber()+"%"));
        }

        if(fieldIsNotEmpty(r.deliveredFrom())) {
            if(fieldIsNotEmpty(r.deliveredTo())) {
                predicates.add(cb.between(order.get("deliveryInformation").get("deliveredAt"), r.deliveredFrom(), r.deliveredTo()));
            }
            else {
                predicates.add(cb.greaterThanOrEqualTo(order.get("deliveryInformation").get("deliveredAt"), r.deliveredFrom()));
            }
        }
        else if(fieldIsNotEmpty(r.deliveredTo())) {
            predicates.add(cb.lessThanOrEqualTo(order.get("deliveryInformation").get("deliveredAt"), r.deliveredTo()));
        }

        OrderStatus status = getEnum(r.status(), OrderStatus.class);
        if(fieldIsNotEmpty(status)) {
            predicates.add(cb.equal(order.get("status"), status));
        }
        OrderType type = getEnum(r.type(), OrderType.class);
        if (fieldIsNotEmpty(type)) {
            predicates.add(cb.equal(order.get("type"), type));
        }

        if(fieldIsNotEmpty(r.createdFrom())) {
            if(fieldIsNotEmpty(r.createdTo())) {
                predicates.add(cb.between(order.get("createdAt"), r.createdFrom(), r.createdTo()));
            }
            else {
                predicates.add(cb.greaterThanOrEqualTo(order.get("createdAt"), r.createdFrom()));
            }
        }
        else if(fieldIsNotEmpty(r.createdTo())) {
            predicates.add(cb.lessThanOrEqualTo(order.get("createdAt"), r.createdTo()));
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }

}

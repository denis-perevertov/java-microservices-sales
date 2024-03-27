package com.dp.deliveryservice.persistence.specification;

import com.dp.deliveryservice.model.ReceiverAddressPageRequest;
import com.dp.deliveryservice.persistence.DeliveryDestinationType;
import com.dp.deliveryservice.persistence.entity.ReceiverAddress;
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
import java.util.Locale;

@RequiredArgsConstructor
public class ReceiverAddressListSpecification implements Specification<ReceiverAddress> {

    private final ReceiverAddressPageRequest request;

    @Override
    public Predicate toPredicate(@NonNull Root<ReceiverAddress> address,
                                 @NonNull CriteriaQuery<?> q,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(fieldIsNotEmpty(request.id())) {
            predicates.add(cb.equal(address.get("id"), request.id()));
        }
        if(fieldIsNotEmpty(request.name())) {
            predicates.add(cb.like(cb.lower(address.get("name")), request.name().toLowerCase(Locale.ROOT)));
        }
        if(fieldIsNotEmpty(request.userId())) {
            predicates.add(cb.equal(address.get("userId"), request.userId()));
        }
        if(fieldIsNotEmpty(request.country())) {
            // todo
        }
        if(fieldIsNotEmpty(request.destinationType())) {
            DeliveryDestinationType deliveryDestinationType = getEnum(request.destinationType(), DeliveryDestinationType.class);
            predicates.add(cb.equal(address.get("destinationType"), deliveryDestinationType));
        }
        if(fieldIsNotEmpty(request.receiverData())) {
            predicates.add(cb.or(
                    cb.like(cb.lower(
                                    cb.concat(
                                            cb.concat(address.get("receiver").get("firstName"), " "),
                                            address.get("receiver").get("lastName")
                                    )
                            ),
                            "%"+request.receiverData().toLowerCase()+"%"),
                    cb.like(address.get("receiver").get("phone"), "%"+request.receiverData()+"%")
            ));
        }
        if(fieldIsNotEmpty(request.region())) {
            predicates.add(cb.or(
                    cb.like(address.get("region").get("id"), "%"+request.region()+"%"),
                    cb.like(address.get("region").get("name"), "%"+request.region()+"%")
            ));
        }
        if(fieldIsNotEmpty(request.city())) {
            predicates.add(cb.or(
                    cb.like(address.get("city").get("id"), "%"+request.city()+"%"),
                    cb.like(address.get("city").get("name"), "%"+request.city()+"%")
            ));
        }
        if(fieldIsNotEmpty(request.house())) {
            predicates.add(cb.like(address.get("houseNumber"), "%"+request.house()+"%"));
        }
        if(fieldIsNotEmpty(request.apartment())) {
            predicates.add(cb.like(address.get("apartmentNumber"), "%"+request.apartment()+"%"));
        }
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}

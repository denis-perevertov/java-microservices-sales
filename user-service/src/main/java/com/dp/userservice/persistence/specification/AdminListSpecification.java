package com.dp.userservice.persistence.specification;

import com.dp.userservice.model.admin.AdminPageRequest;
import com.dp.userservice.persistence.entity.Admin;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AdminListSpecification implements Specification<Admin> {
    private final AdminPageRequest request;

    @Override
    public Predicate toPredicate(@NonNull Root<Admin> admin,
                                 @NonNull CriteriaQuery<?> q,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(request.id() != null && request.id() > 0) {
            predicates.add(cb.equal(admin.get("id"), request.id()));
        }
        if(request.email() != null && !request.email().isEmpty()) {
            predicates.add(cb.like(admin.get("email"), "%"+request.email()+"%"));
        }
        if(request.name() != null && !request.name().isEmpty()) {
            predicates.add(cb.like(
                    cb.concat(
                            cb.concat(admin.get("firstName"), " "),
                            cb.lower(admin.get("lastName"))
                    ),
                    "%"+request.name()+"%"
            ));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

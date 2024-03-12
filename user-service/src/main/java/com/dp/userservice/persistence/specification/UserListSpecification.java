package com.dp.userservice.persistence.specification;

import com.dp.userservice.model.user.UserPageRequest;
import com.dp.userservice.persistence.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import static com.dp.utils.ValidationUtils.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserListSpecification implements Specification<User> {
    private final UserPageRequest request;

    @Override
    public Predicate toPredicate(@NonNull Root<User> user,
                                 @NonNull CriteriaQuery<?> q,
                                 @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(request.id() != null && request.id() > 0) {
            predicates.add(cb.equal(user.get("id"), request.id()));
        }
        if(fieldIsNotEmpty(request.name())) {
            predicates.add(cb.like(
                    cb.concat(
                            cb.concat(user.get("profileDetails").get("firstName"), " "),
                            cb.lower(user.get("profileDetails").get("lastName"))
                    ),
                    "%"+request.name()+"%"
            ));
        }
        if(fieldIsNotEmpty(request.email())) {
            predicates.add(cb.like(user.get("email"), "%"+request.email()+"%"));
        }
        if(fieldIsNotEmpty(request.phone())) {
            predicates.add(cb.like(user.get("profileDetails").get("phone"), "%"+request.email()+"%"));
        }
        if(request.beginDate() != null
            && request.endDate() != null
            && request.beginDate().isBefore(request.endDate())) {
            predicates.add(
                    cb.between(
                            user.get("profileDetails").get("birthdate"),
                            request.beginDate(),
                            request.endDate()
                    )
            );
        }
        if(fieldIsNotEmpty(request.gender())) {
            predicates.add(cb.equal(user.get("profileDetails").get("gender"), request.gender()));
        }
        if(request.balanceFrom() != null
                && request.balanceTo() != null
                && request.balanceFrom() < request.balanceTo()) {
            predicates.add(
                    cb.between(
                            user.get("financeDetails").get("balance"),
                            request.balanceFrom(),
                            request.balanceTo()
                    )
            );
        }

        // todo add total transaction sum

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

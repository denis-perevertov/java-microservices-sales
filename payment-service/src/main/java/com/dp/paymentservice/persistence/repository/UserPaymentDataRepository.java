package com.dp.paymentservice.persistence.repository;

import com.dp.paymentservice.persistence.entity.UserPaymentData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserPaymentDataRepository extends MongoRepository<UserPaymentData, String> {

    Optional<UserPaymentData> findByUserId(String userId);
    Optional<UserPaymentData> findByPaymentProfileId(String paymentProfileId);

}

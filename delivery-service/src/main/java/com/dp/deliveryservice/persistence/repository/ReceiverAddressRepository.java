package com.dp.deliveryservice.persistence.repository;

import com.dp.deliveryservice.persistence.entity.ReceiverAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceiverAddressRepository extends JpaRepository<ReceiverAddress, Long>, JpaSpecificationExecutor<ReceiverAddress> {
    List<ReceiverAddress> findByUserId(Long userId);

    @Modifying
    @Query("""
           UPDATE ReceiverAddress ra
           SET ra.isDefault = CASE
           WHEN (ra.userId = :userId AND ra.id = :defaultAddressId) THEN true
           WHEN (ra.userId = :userId AND ra.id != :defaultAddressId) THEN false
           """)
    void setUserReceiverAddressAsDefault(Long userId, Long defaultAddressId);
}

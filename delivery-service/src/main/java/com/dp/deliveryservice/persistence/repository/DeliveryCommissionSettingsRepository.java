package com.dp.deliveryservice.persistence.repository;

import com.dp.deliveryservice.persistence.entity.DeliveryCommissionSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryCommissionSettingsRepository extends JpaRepository<DeliveryCommissionSettings, Long> {
    Optional<DeliveryCommissionSettings> findFirstBy();
}

package com.dp.deliveryservice.persistence.repository;

import com.dp.deliveryservice.persistence.entity.WarehouseAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WarehouseAddressRepository extends JpaRepository<WarehouseAddress, Long>, JpaSpecificationExecutor<WarehouseAddress> {
}

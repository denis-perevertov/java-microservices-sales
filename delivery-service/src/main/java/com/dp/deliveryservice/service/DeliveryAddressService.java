package com.dp.deliveryservice.service;

import com.dp.deliveryservice.persistence.entity.ReceiverAddress;
import com.dp.deliveryservice.persistence.entity.WarehouseAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DeliveryAddressService {

    List<ReceiverAddress> getUserReceiverAddresses(Long userId);
    Page<ReceiverAddress> getReceiverAddressesPage(Specification<ReceiverAddress> spec, Pageable pageable);
    ReceiverAddress getReceiverAddress(Long addressId);
    ReceiverAddress saveReceiverAddress(ReceiverAddress receiverAddress);
    void deleteReceiverAddress(Long addressId);

    void setReceiverAddressAsDefault(Long addressId);

    List<WarehouseAddress> getUserWarehouseAddresses(Long userId);
    List<WarehouseAddress> getAllWarehouseAddresses();
    Page<WarehouseAddress> getWarehouseAddressesPage(Specification<WarehouseAddress> spec, Pageable pageable);
    WarehouseAddress getWarehouseAddress(Long addressId);
    WarehouseAddress saveWarehouseAddress(WarehouseAddress address);
    void deleteWarehouseAddress(Long addressId);

}

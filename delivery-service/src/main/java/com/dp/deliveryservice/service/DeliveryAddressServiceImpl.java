package com.dp.deliveryservice.service;

import com.dp.deliveryservice.persistence.entity.ReceiverAddress;
import com.dp.deliveryservice.persistence.entity.WarehouseAddress;
import com.dp.deliveryservice.persistence.repository.ReceiverAddressRepository;
import com.dp.deliveryservice.persistence.repository.WarehouseAddressRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryAddressServiceImpl implements DeliveryAddressService{

    private final ReceiverAddressRepository receiverAddressRepository;
    private final WarehouseAddressRepository warehouseAddressRepository;

    @Override
    public List<ReceiverAddress> getUserReceiverAddresses(Long userId) {
        log.info("getting receiver addresses for user (userID:{})", userId);
        return receiverAddressRepository.findByUserId(userId);
    }

    @Override
    public ReceiverAddress getReceiverAddress(Long addressId) {
        log.info("getting receiver address (addressID:{})", addressId);
        return receiverAddressRepository.findById(addressId).orElseThrow(() -> new EntityNotFoundException("Address not found!"));
    }

    @Override
    public ReceiverAddress saveReceiverAddress(ReceiverAddress receiverAddress) {
        receiverAddress = receiverAddressRepository.save(receiverAddress);
        log.info("receiver address saved (addressID:{})", receiverAddress);
        return receiverAddress;
    }

    @Override
    public void deleteReceiverAddress(Long addressId) {
        receiverAddressRepository.deleteById(addressId);
        log.info("receiver address deleted (addressID:{})", addressId);
    }

    @Override
    @Transactional
    public void setReceiverAddressAsDefault(Long addressId) {
        ReceiverAddress address = getReceiverAddress(addressId);
        receiverAddressRepository.setUserReceiverAddressAsDefault(address.getUserId(), addressId);
        log.info("set receiver address as default (userID: {}, addressID: {})", address.getUserId(), addressId);
    }

    @Override
    public List<WarehouseAddress> getUserWarehouseAddresses(Long userId) {
        return null;
    }

    @Override
    public List<WarehouseAddress> getAllWarehouseAddresses() {
        log.info("getting all warehouse addresses");
        return warehouseAddressRepository.findAll();
    }

    @Override
    public Page<ReceiverAddress> getReceiverAddressesPage(Specification<ReceiverAddress> spec, Pageable pageable) {
        Page<ReceiverAddress> page = receiverAddressRepository.findAll(spec, pageable);
        log.info("fetched warehouse addresses (page {}/{})", page.getNumber(), page.getTotalPages());
        return page;
    }

    @Override
    public Page<WarehouseAddress> getWarehouseAddressesPage(Specification<WarehouseAddress> spec, Pageable pageable) {
        Page<WarehouseAddress> page = warehouseAddressRepository.findAll(spec, pageable);
        log.info("fetched warehouse addresses (page {}/{})", page.getNumber(), page.getTotalPages());
        return page;
    }

    @Override
    public WarehouseAddress getWarehouseAddress(Long addressId) {
        log.info("getting warehouse address (ID:{})", addressId);
        return warehouseAddressRepository.findById(addressId).orElseThrow(() -> new EntityNotFoundException("Address not found!"));
    }

    @Override
    public WarehouseAddress saveWarehouseAddress(WarehouseAddress address) {
        address = warehouseAddressRepository.save(address);
        log.info("warehouse address saved (addressID:{})", address);
        return address;
    }

    @Override
    public void deleteWarehouseAddress(Long addressId) {
        warehouseAddressRepository.deleteById(addressId);
        log.info("warehouse address deleted (addressID:{})", addressId);
    }
}

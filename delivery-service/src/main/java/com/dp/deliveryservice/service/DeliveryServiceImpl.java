package com.dp.deliveryservice.service;

import com.dp.deliveryservice.model.CalculateCostRequest;
import com.dp.deliveryservice.model.CalculateCostResponse;
import com.dp.deliveryservice.persistence.DeliveryMethod;
import com.dp.deliveryservice.persistence.entity.DeliveryCommissionSettings;
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
public class DeliveryServiceImpl implements DeliveryService {


    private final DeliveryCommissionSettingsService deliveryCommissionService;

    @Override
    public CalculateCostResponse calculateDeliveryCost(CalculateCostRequest request) {
        DeliveryCommissionSettings commissionSettings = deliveryCommissionService.getSettings();
        return new CalculateCostResponse(
                request.deliveryMethod().equals(DeliveryMethod.AIR)
                        ? commissionSettings.getAirDeliveryFeeSetting()
                        : commissionSettings.getSeaDeliveryFeeSetting(),
                commissionSettings.getInsuranceSetting(),
                commissionSettings.getProcessingSetting(),
                commissionSettings.getCommissionSetting(),
                request.price()
        );
    }


}

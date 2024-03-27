package com.dp.deliveryservice.mapper;

import com.dp.deliveryservice.model.ReceiverAddressCreateRequest;
import com.dp.deliveryservice.model.WarehouseAddressCreateRequest;
import com.dp.deliveryservice.persistence.DeliveryDestinationType;
import com.dp.deliveryservice.persistence.entity.NPLocationEntity;
import com.dp.deliveryservice.persistence.entity.Receiver;
import com.dp.deliveryservice.persistence.entity.ReceiverAddress;
import com.dp.deliveryservice.persistence.entity.WarehouseAddress;
import com.dp.deliveryservice.persistence.repository.WarehouseAddressRepository;
import com.dp.deliveryservice.service.DeliveryAddressService;
import com.dp.deliveryservice.service.DeliveryService;
import com.dp.utils.Country;
import com.dp.utils.EnumUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryAddressMapper {

    private final DeliveryAddressService deliveryAddressService;

    public WarehouseAddress fromWarehouseCreateRequestToAddress(WarehouseAddressCreateRequest request) {
        WarehouseAddress address = new WarehouseAddress();
        return mapAddress(address, request);
    }

    public WarehouseAddress fromWarehouseCreateRequestToAddress(WarehouseAddressCreateRequest request,
                                                                Long id) {
        WarehouseAddress address = deliveryAddressService.getWarehouseAddress(id);
        return mapAddress(address, request);
    }

    public ReceiverAddress fromReceiverCreateRequestToAddress(ReceiverAddressCreateRequest request) {
        ReceiverAddress address = new ReceiverAddress();
        return mapAddress(address, request);
    }

    public ReceiverAddress fromReceiverCreateRequestToAddress(ReceiverAddressCreateRequest request,
                                                              Long id) {
        ReceiverAddress address = deliveryAddressService.getReceiverAddress(id);
        return mapAddress(address, request);
    }

    private WarehouseAddress mapAddress(WarehouseAddress address,
                                       WarehouseAddressCreateRequest request) {
        address.setCity(request.city())
                .setZip(request.zip())
                .setState(request.state())
                .setStreet(request.street())
                .setPhone(request.phone());
        return address;
    }

    private ReceiverAddress mapAddress(ReceiverAddress address,
                                      ReceiverAddressCreateRequest request) {
        address.setName(request.name())
                .setUserId(request.userId())
                .setCountry(EnumUtils.getEnum(request.countryCode(), Country.class))
                .setDestinationType(EnumUtils.getEnum(request.deliveryDestinationType(), DeliveryDestinationType.class))
                .setReceiver(new Receiver()
                        .setFirstName(request.receiver().firstName())
                        .setLastName(request.receiver().lastName())
                        .setPhone(request.receiver().phone()))
                .setRegion(new NPLocationEntity()
                        .setId(request.region().id())
                        .setName(request.region().name()))
                .setCity(new NPLocationEntity()
                        .setId(request.city().id())
                        .setName(request.city().name()))
                .setWarehouse(new NPLocationEntity()
                        .setId(request.warehouse().id())
                        .setName(request.warehouse().name()))
                .setStreet(new NPLocationEntity()
                        .setId(request.street().id())
                        .setName(request.street().name()))
                .setHouseNumber(request.houseNumber())
                .setApartmentNumber(request.apartmentNumber());
        return address;
    }

}

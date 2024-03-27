package com.dp.deliveryservice.controller;

import com.dp.deliveryservice.mapper.DeliveryAddressMapper;
import com.dp.deliveryservice.model.WarehouseAddressCreateRequest;
import com.dp.deliveryservice.model.WarehouseAddressPageRequest;
import com.dp.deliveryservice.persistence.entity.WarehouseAddress;
import com.dp.deliveryservice.persistence.specification.WarehouseAddressListSpecification;
import com.dp.deliveryservice.service.DeliveryAddressService;
import com.dp.deliveryservice.service.DeliveryService;
import com.dp.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/address/warehouse")
public class DeliveryWarehouseAddressController {

    private final DeliveryAddressService addressService;
    private final DeliveryAddressMapper deliveryAddressMapper;

    @GetMapping({"", "/"})
    public ResponseEntity<?> getWarehouseAddressesPage(WarehouseAddressPageRequest request) {
        return ResponseEntity.ok(addressService.getWarehouseAddressesPage(
                new WarehouseAddressListSpecification(request),
                PageRequest.of(request.page(), request.size())
        ));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserWarehouseAddresses(@PathVariable Long userId) {
        // connect to user service ?
        return ResponseEntity.ok(addressService.getUserWarehouseAddresses(userId));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<?> createWarehouseAddress(@RequestBody @Valid WarehouseAddressCreateRequest request,
                                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        WarehouseAddress address = deliveryAddressMapper.fromWarehouseCreateRequestToAddress(request);
        return ResponseEntity.ok(addressService.saveWarehouseAddress(address));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<?> updateWarehouseAddress(@PathVariable Long addressId,
                                                    @RequestBody @Valid WarehouseAddressCreateRequest request,
                                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        WarehouseAddress address = deliveryAddressMapper.fromWarehouseCreateRequestToAddress(request, addressId);
        return ResponseEntity.ok(addressService.saveWarehouseAddress(address));
    }

    @DeleteMapping({"", "/"})
    public ResponseEntity<?> deleteWarehouseAddress(@RequestParam Long id) {
        addressService.deleteWarehouseAddress(id);
        return ResponseEntity.ok().build();
    }
}

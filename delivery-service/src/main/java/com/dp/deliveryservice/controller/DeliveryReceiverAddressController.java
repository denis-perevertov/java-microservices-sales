package com.dp.deliveryservice.controller;

import com.dp.deliveryservice.mapper.DeliveryAddressMapper;
import com.dp.deliveryservice.model.ReceiverAddressCreateRequest;
import com.dp.deliveryservice.model.ReceiverAddressPageRequest;
import com.dp.deliveryservice.model.WarehouseAddressCreateRequest;
import com.dp.deliveryservice.model.WarehouseAddressPageRequest;
import com.dp.deliveryservice.persistence.entity.ReceiverAddress;
import com.dp.deliveryservice.persistence.entity.WarehouseAddress;
import com.dp.deliveryservice.persistence.specification.ReceiverAddressListSpecification;
import com.dp.deliveryservice.persistence.specification.WarehouseAddressListSpecification;
import com.dp.deliveryservice.service.DeliveryAddressService;
import com.dp.deliveryservice.service.DeliveryService;
import com.dp.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/address/receiver")
public class DeliveryReceiverAddressController {

    private final DeliveryAddressService addressService;
    private final DeliveryAddressMapper deliveryAddressMapper;
    private final RestClient restClient;

    @GetMapping({"", "/"})
    public ResponseEntity<?> getReceiverAddressesPage(ReceiverAddressPageRequest request) {
        return ResponseEntity.ok(addressService.getReceiverAddressesPage(
                new ReceiverAddressListSpecification(request),
                PageRequest.of(request.page(), request.size())
        ));
    }

    @GetMapping("/{receiverId}")
    public ResponseEntity<?> getSingleReceiverAddresses(@PathVariable Long receiverId) {
        return ResponseEntity.ok(addressService.getUserReceiverAddresses(receiverId));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<?> createReceiverAddress(@RequestBody @Valid ReceiverAddressCreateRequest request,
                                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        ReceiverAddress address = deliveryAddressMapper.fromReceiverCreateRequestToAddress(request);
        return ResponseEntity.ok(addressService.saveReceiverAddress(address));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<?> updateReceiverAddress(@PathVariable Long addressId,
                                                   @RequestBody @Valid ReceiverAddressCreateRequest request,
                                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        ReceiverAddress address = deliveryAddressMapper.fromReceiverCreateRequestToAddress(request, addressId);
        return ResponseEntity.ok(addressService.saveReceiverAddress(address));
    }

    @DeleteMapping({"", "/"})
    public ResponseEntity<?> deleteReceiverAddress(@RequestParam Long id) {
        addressService.deleteReceiverAddress(id);
        return ResponseEntity.ok().build();
    }


}

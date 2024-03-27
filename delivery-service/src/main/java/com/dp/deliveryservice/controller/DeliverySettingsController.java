package com.dp.deliveryservice.controller;

import com.dp.deliveryservice.mapper.DeliveryCommissionMapper;
import com.dp.deliveryservice.model.DeliveryCommissionSettingsDTO;
import com.dp.deliveryservice.service.DeliveryCommissionSettingsService;
import com.dp.deliveryservice.service.DeliveryService;
import com.dp.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/settings")
public class DeliverySettingsController {

    private final DeliveryCommissionSettingsService commissionSettingsService;
    private final DeliveryCommissionMapper deliveryCommissionMapper;

    @GetMapping({"", "/"})
    public ResponseEntity<?> getCommissionSettings() {
        return ResponseEntity.ok(deliveryCommissionMapper.settingsToDTO(commissionSettingsService.getSettings()));
    }

    @PutMapping({"", "/"})
    public ResponseEntity<?> updateCommissionSettings(@RequestBody @Valid DeliveryCommissionSettingsDTO dto,
                                                      BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(bindingResult));
        }
        commissionSettingsService.saveSettings(deliveryCommissionMapper.dtoToSettings(dto));
        return ResponseEntity.ok().build();
    }

}

package com.dp.deliveryservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
public class DeliveryCommissionSettings {

    @Id
    private Long id;

    @Column(nullable = false)
    private Double commissionSetting;
    @Column(nullable = false)
    private Double insuranceSetting;
    @Column(nullable = false)
    private Double processingSetting;
    @Column(nullable = false)
    private Double airDeliveryFeeSetting;
    @Column(nullable = false)
    private Double seaDeliveryFeeSetting;

}

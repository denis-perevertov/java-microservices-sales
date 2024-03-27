package com.dp.deliveryservice.persistence.entity;

import com.dp.deliveryservice.persistence.DeliveryDestinationType;
import com.dp.utils.Country;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
public class ReceiverAddress {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private boolean isDefault;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryDestinationType destinationType;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "receiver_firstName")),
            @AttributeOverride(name = "lastName", column = @Column(name = "receiver_lastName")),
            @AttributeOverride(name = "phone", column = @Column(name = "receiver_phone"))
    })
    private Receiver receiver;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "region_id")),
            @AttributeOverride(name = "name", column = @Column(name = "region_name")),
    })
    private NPLocationEntity region;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "warehouse_id")),
            @AttributeOverride(name = "name", column = @Column(name = "warehouse_name")),
    })
    private NPLocationEntity warehouse;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "city_id")),
            @AttributeOverride(name = "name", column = @Column(name = "city_name")),
    })
    private NPLocationEntity city;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "street_id")),
            @AttributeOverride(name = "name", column = @Column(name = "street_name")),
    })
    private NPLocationEntity street;

    private String houseNumber;
    private String apartmentNumber;

}

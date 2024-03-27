package com.dp.deliveryservice.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Embeddable
@Accessors(chain = true)
public class Receiver {
    private String firstName;
    private String lastName;
    private String phone;
}

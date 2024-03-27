package com.dp.deliveryservice.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Embeddable
@Accessors(chain = true)
public class NPLocationEntity {
    private String id;
    private String name;
}

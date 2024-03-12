package com.dp.paymentservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_payment_data")
@AllArgsConstructor
@Accessors(chain = true)
public class UserPaymentData {

    private String userId;
    private String paymentProfileId;

}

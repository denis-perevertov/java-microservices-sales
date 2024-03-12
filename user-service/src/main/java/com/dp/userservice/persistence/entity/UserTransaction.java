package com.dp.userservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "user_transactions")
public class UserTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    ZonedDateTime datetime;
    @Column(nullable = false)
    Double sum;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserTransaction.Type type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserTransaction.Method method;

    public enum Type {
        ORDER_PAYMENT,
        BALANCE_TOP_UP,
        BALANCE_WITHDRAWAL
    }

    public enum Method {
        BALANCE
    }
}

package com.dp.userservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_finance_details")
@Accessors(chain = true)
public class UserFinanceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    Double balance;
    @Column(nullable = false, columnDefinition = "boolean default false")
    Boolean automaticDebtDeduction;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserTransaction> transactions = new ArrayList<>();

}

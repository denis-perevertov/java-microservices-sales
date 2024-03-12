package com.dp.userservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(unique = true, nullable = false)
    String email;
    @Column(unique = true, nullable = false)
    String authId;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    UserProfileDetails profileDetails = new UserProfileDetails();
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    UserFinanceDetails financeDetails = new UserFinanceDetails();

    @ElementCollection
    List<Long> favorites = new ArrayList<>();

}


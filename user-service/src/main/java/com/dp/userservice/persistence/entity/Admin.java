package com.dp.userservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Accessors(chain = true)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false, unique = true)
    String authId;
    @Column(length = 50)
    String firstName;
    @Column(length = 50)
    String lastName;
    String avatar;

}

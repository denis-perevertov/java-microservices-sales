package com.dp.userservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "user_profile_details")
@Accessors(chain = true)
public class UserProfileDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(length = 50)
    String firstName;
    @Column(length = 50)
    String lastName;
    String avatar;
    ZonedDateTime birthdate;
    @Column(length = 20)
    String phone;
    @Enumerated(EnumType.STRING)
    UserProfileDetails.Gender gender;

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

}

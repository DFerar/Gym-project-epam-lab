package com.gym.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private String userName;
    private String password;
    private Boolean isActive;
}

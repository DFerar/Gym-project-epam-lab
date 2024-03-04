package com.gym.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    // TODO why wrappers?
    private Integer userId;
    private String firstName;
    private String lastName;
    // TODO why string?
    private String dateOfBirth;
    private String address;
    private String userName;
    private String password;
    private Boolean isActive;
}

package com.gym.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    private int userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String userName;
    private String password;
    private Boolean isActive;
}

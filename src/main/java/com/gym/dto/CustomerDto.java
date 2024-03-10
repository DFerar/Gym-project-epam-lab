package com.gym.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class CustomerDto {
    private Integer id;
    private Date dateOfBirth;
    private String address;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean isActive;
}

package com.gym.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instructor {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private Boolean isActive;
    private String specialization;
}

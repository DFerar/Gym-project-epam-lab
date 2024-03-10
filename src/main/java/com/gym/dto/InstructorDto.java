package com.gym.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstructorDto {
    private Integer id;
    private String specialization;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean isActive;
}

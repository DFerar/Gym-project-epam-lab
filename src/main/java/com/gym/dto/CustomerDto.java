package com.gym.dto;


import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

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
    private Set<TrainingDto> trainings;
}

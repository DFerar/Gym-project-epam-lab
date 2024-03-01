package com.gym.dto;


import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstructorDto {
    private Integer id;
    private Integer specialization;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean isActive;
    private Set<TrainingDto> trainings;
}

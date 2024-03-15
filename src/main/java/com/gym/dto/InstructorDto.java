package com.gym.dto;


import com.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstructorDto {
    private Long id;
    private TrainingType specialization;
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean isActive;
}

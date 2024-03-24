package com.gym.responseDto.instructorResponse;

import com.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInstructorProfileResponseDto {
    private String userName;
    private String firstName;
    private String lastName;
    private TrainingType specialization;
    private Boolean isActive;
    private List<CustomerForInstructorResponseDto> customers;
}

package com.gym.responseDto.customerResponse;

import com.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstructorForCustomerResponseDto {
    private String userName;
    private String firstName;
    private String lastName;
    private TrainingType specialization;
}

package com.gym.responseDto.instructorResponse;

import com.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetNotAssignedOnCustomerInstructorsResponseDto {
    private String userName;
    private String firstName;
    private String lastName;
    private TrainingType specialization;
}

package com.gym.responseDto.instructorResponse;

import com.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNotAssignedOnCustomerInstructorsResponseDto {
    private String userName;
    private String firstName;
    private String lastName;
    private TrainingType specialization;
}

package com.gym.requestDto.instructorRequest;

import com.gym.entity.TrainingType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInstructorRequestDto {
    @NotEmpty(message = "Should not be empty")
    private String firstName;
    @NotEmpty(message = "Should not be empty")
    private String lastName;
    @NotEmpty(message = "Should not be empty")
    private TrainingType specialization;
}
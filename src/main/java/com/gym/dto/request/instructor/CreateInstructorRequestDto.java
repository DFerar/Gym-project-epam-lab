package com.gym.dto.request.instructor;

import com.gym.entity.TrainingType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private TrainingType specialization;
}
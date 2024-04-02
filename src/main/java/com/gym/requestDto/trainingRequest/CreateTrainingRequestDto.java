package com.gym.requestDto.trainingRequest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrainingRequestDto {
    @NotEmpty(message = "Should not be empty")
    private String customerUserName;
    @NotEmpty(message = "Should not be empty")
    private String instructorUserName;
    @NotEmpty(message = "Should not be empty")
    private String trainingName;
    @NotNull(message = "Should not be empty")
    private LocalDate trainingDate;
    @NotNull(message = "Should not be empty")
    private Integer trainingDuration;
}

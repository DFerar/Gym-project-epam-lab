package com.gym.requestDto.trainingRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrainingRequestDto {
    @NotNull
    private String customerUserName;
    @NotNull
    private String instructorUserName;
    @NotNull
    private String trainingName;
    @NotNull
    private String trainingDate;
    @NotNull
    private Integer trainingDuration;
}

package com.gym.requestDto.trainingRequest;

import com.gym.entity.TrainingType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class CreateTrainingRequestDto {
    @NotNull
    private String customerUserName;
    @NotNull
    private String instructorUserName;
    @NotNull
    private TrainingType trainingName;
    @NotNull
    private Date trainingDate;
    @NotNull
    private Integer trainingDuration;
}

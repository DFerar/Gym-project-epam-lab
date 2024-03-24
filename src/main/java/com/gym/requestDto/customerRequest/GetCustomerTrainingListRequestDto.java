package com.gym.requestDto.customerRequest;

import com.gym.entity.TrainingType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerTrainingListRequestDto {
    @NotNull
    private String userName;
    private String fromDate;
    private String toDate;
    private String instructorName;
    private TrainingType trainingType;
}

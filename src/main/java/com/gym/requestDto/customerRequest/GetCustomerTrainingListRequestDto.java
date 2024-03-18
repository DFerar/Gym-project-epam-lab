package com.gym.requestDto.customerRequest;

import com.gym.entity.TrainingType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class GetCustomerTrainingListRequestDto {
    @NotNull
    private String userName;
    private Date fromDate;
    private Date toDate;
    private String instructorName;
    private TrainingType trainingType;
}

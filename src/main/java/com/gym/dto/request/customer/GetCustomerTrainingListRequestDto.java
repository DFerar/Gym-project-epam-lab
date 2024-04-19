package com.gym.dto.request.customer;

import com.gym.entity.TrainingType;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerTrainingListRequestDto {
    @NotEmpty(message = "Should not be empty")
    private String userName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String instructorName;
    private TrainingType trainingType;
}

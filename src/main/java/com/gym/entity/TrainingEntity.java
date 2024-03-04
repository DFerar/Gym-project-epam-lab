package com.gym.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingEntity {
    private int trainingId;
    private int customerId;
    private int instructorId;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private String trainingDuration;
}

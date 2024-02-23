package com.gym.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    private Integer trainingId;
    private Integer customerId;
    private Integer instructorId;
    private String trainingName;
    private TrainingType trainingType;
    private String trainingDate;
    private String trainingDuration;
}

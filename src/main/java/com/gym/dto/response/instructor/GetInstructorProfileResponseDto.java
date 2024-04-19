package com.gym.dto.response.instructor;

import com.gym.entity.TrainingType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInstructorProfileResponseDto {
    private String firstName;
    private String lastName;
    private TrainingType specialization;
    private Boolean isActive;
    private List<CustomerForInstructorResponseDto> customers;
}

package com.gym.requestDto.instructorRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInstructorTrainingsRequestDto {
    @NotNull
    private String userName;
    private String fromDate;
    private String toDate;
    private String customerName;
}

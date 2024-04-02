package com.gym.requestDto.instructorRequest;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInstructorTrainingsRequestDto {
    @NotEmpty(message = "Should not be empty")
    private String userName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String customerName;
}

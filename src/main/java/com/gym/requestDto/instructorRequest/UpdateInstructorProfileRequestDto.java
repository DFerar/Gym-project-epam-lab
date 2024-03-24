package com.gym.requestDto.instructorRequest;

import com.gym.entity.TrainingType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInstructorProfileRequestDto {
    @NotNull
    private String userName;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @ReadOnlyProperty
    private TrainingType specialization;
    @NotNull
    private Boolean isActive;
}

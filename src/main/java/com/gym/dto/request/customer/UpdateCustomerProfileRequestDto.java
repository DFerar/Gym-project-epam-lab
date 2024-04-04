package com.gym.dto.request.customer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerProfileRequestDto {
    @NotEmpty(message = "Should not be empty")
    private String userName;
    @NotEmpty(message = "Should not be empty")
    private String firstName;
    @NotEmpty(message = "Should not be empty")
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    @NotNull(message = "Should not be empty")
    private Boolean isActive;
}

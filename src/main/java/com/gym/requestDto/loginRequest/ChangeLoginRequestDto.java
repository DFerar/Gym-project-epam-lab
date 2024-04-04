package com.gym.requestDto.loginRequest;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeLoginRequestDto {
    @NotEmpty(message = "Should not be empty")
    private String userName;
    @NotEmpty(message = "Should not be empty")
    private String oldPassword;
    @NotEmpty(message = "Should not be empty")
    private String newPassword;
}

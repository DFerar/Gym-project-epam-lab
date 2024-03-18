package com.gym.requestDto.loginRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeLoginRequestDto {
    @NotNull
    private String userName;
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}

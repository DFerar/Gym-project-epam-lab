package com.gym.requestDto.loginRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {
    @NotNull
    private String userName;
    @NotNull
    private String password;
}

package com.gym.controller;


import com.gym.requestDto.loginRequest.ChangeLoginRequestDto;
import com.gym.requestDto.loginRequest.LoginRequestDto;
import com.gym.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "LoginController", description = "API for Authentication operations")
public class LoginController {
    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    @Operation(summary = "Login", description = "Logs in a user and returns OK status")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        authenticationService.matchCredentials(loginRequestDto.getUserName(), loginRequestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Change login", description = "Changes a user's password and returns OK status")
    public ResponseEntity<String> changeLogin(@Valid @RequestBody ChangeLoginRequestDto changeLoginRequestDto) {
        authenticationService.changeUsersPassword(
            changeLoginRequestDto.getUserName(), changeLoginRequestDto.getOldPassword(),
            changeLoginRequestDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

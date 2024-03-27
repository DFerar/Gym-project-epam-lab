package com.gym.controller;


import com.gym.requestDto.loginRequest.ChangeLoginRequestDto;
import com.gym.requestDto.loginRequest.LoginRequestDto;
import com.gym.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//@Tag(name = "LoginController", description = "API for Authentication operations")
public class LoginController {
    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    //@Operation(summary = "Login", description = "Logs in a user and returns OK status")
    public ResponseEntity<String> login(@Valid @RequestBody/*(description = "User login data", required = true,
            content = @Content(schema = @Schema(implementation = LoginRequestDto.class)))*/
                                        LoginRequestDto loginRequestDto) {
        authenticationService.matchCredentials(loginRequestDto.getUserName(), loginRequestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    //@Operation(summary = "Change login", description = "Changes a user's password and returns OK status")
    public ResponseEntity<String> changeLogin(@Valid @RequestBody/*(description = "New login data", required = true,
            content = @Content(schema = @Schema(implementation = ChangeLoginRequestDto.class)))*/
                                              ChangeLoginRequestDto changeLoginRequestDto) {
        authenticationService.changeUsersPassword(
                changeLoginRequestDto.getUserName(), changeLoginRequestDto.getOldPassword(),
                changeLoginRequestDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

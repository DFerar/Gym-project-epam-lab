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
public class LoginController {
    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        if (authenticationService.matchCredentials(loginRequestDto.getUserName(), loginRequestDto.getPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> changeLogin(@Valid @RequestBody ChangeLoginRequestDto changeLoginRequestDto) {
        authenticationService.changeUsersPassword(
                changeLoginRequestDto.getUserName(), changeLoginRequestDto.getOldPassword(),
                changeLoginRequestDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

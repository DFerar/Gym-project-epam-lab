package com.gym.controller;


import com.gym.dto.request.login.ChangeLoginRequestDto;
import com.gym.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "PasswordChangeController", description = "API for changing password")
public class PasswordChangeController {
    private final AuthenticationService authenticationService;
    /**
     * This is @PutMapping("/update") or changeLogin method.
     * It changes a user's password after validating the old one
     * and returns HTTP OK status.
     *
     * @param changeLoginRequestDto The client request body containing old and new passwords.
     * @return {@code ResponseEntity<String>} An HTTP status indicating the success or failure of the password change operation.
     */
    @PutMapping("/update")
    @Operation(summary = "Change login", description = "Changes a user's password and returns OK status")
    public ResponseEntity<String> changeLogin(@Valid @RequestBody ChangeLoginRequestDto changeLoginRequestDto) {
        authenticationService.changeUsersPassword(
            changeLoginRequestDto.getUserName(), changeLoginRequestDto.getOldPassword(),
            changeLoginRequestDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

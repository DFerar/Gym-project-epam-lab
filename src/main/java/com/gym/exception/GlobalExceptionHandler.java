package com.gym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = LoginException.class)
    public ResponseEntity<Object> handleLoginException(Exception e) {
        return new ResponseEntity<>("Wrong credentials: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(Exception e) {
        return new ResponseEntity<>("Cannot find customer: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InstructorNotFoundException.class)
    public ResponseEntity<Object> handleInstructorNotFoundException(Exception e) {
        return new ResponseEntity<>("Cannot find instructor: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleIUserNotFoundException(Exception e) {
        return new ResponseEntity<>("Cannot find user: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TrainingTypeNotFoundException.class)
    public ResponseEntity<Object> handleITrainingTypeNotFoundException(Exception e) {
        return new ResponseEntity<>("Cannot find training type: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

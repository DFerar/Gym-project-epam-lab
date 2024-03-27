package com.gym.exceptionHandler;

public class InstructorNotFoundException extends RuntimeException{
    public InstructorNotFoundException(String message) {
        super(message);
    }
}

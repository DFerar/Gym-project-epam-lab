package com.gym.exceptionHandler;

public class TrainingTypeNotFoundException extends RuntimeException{
    public TrainingTypeNotFoundException(String message) {
        super(message);
    }
}

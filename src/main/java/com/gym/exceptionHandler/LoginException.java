package com.gym.exceptionHandler;

public class LoginException extends RuntimeException{
    public LoginException(String message) {
        super(message);
    }
}

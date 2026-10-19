package com.sungsimdang.project.exception;

public class LoginFailedException extends RuntimeException{

    public LoginFailedException(String message) {
        super(message);
    }
}

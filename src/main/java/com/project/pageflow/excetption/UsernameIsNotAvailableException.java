package com.project.pageflow.excetption;

public class UsernameIsNotAvailableException extends RuntimeException{
    public UsernameIsNotAvailableException(String message) {
        super(message);
    }
}

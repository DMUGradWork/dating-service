package com.grewmeet.datingservice.exception;

public class DatingEventNotFoundException extends RuntimeException {
    public DatingEventNotFoundException(String message) {
        super(message);
    }
}

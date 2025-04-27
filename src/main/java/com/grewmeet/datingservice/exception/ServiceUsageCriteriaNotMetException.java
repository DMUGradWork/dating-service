package com.grewmeet.datingservice.exception;

public class ServiceUsageCriteriaNotMetException extends RuntimeException {
    public ServiceUsageCriteriaNotMetException(String message) {
        super(message);
    }
}

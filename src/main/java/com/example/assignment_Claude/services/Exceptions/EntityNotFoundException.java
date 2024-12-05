package com.example.assignment_Claude.services.Exceptions;

// Custom Exception
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
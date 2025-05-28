package ru.javawebinar.topjavagraduation.validation;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    DATA_CONFLICT_ERROR("DATA_CONFLICT", "Failed to write data to repository due to data conflict", HttpStatus.CONFLICT),
    DATA_NOT_FOUND("DATA_NOT_FOUND", "Data is not found", HttpStatus.UNPROCESSABLE_ENTITY),
    ILLEGAL_OPERATION("ILLEGAL_OPERATION", "Illegal operation", HttpStatus.UNPROCESSABLE_ENTITY),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR("VALIDATION_ERROR", "Validation error", HttpStatus.UNPROCESSABLE_ENTITY),
    WRONG_REQUEST("WRONG_REQUEST", "Bad request", HttpStatus.BAD_REQUEST);

    private final String name;
    private final String message;
    private final HttpStatus status;

    ErrorType(String name, String message, HttpStatus status) {
        this.name = name;
        this.message = message;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

package ru.javawebinar.topjavagraduation.validation.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    DATA_CONFLICT_ERROR("DATA_CONFLICT", HttpStatus.CONFLICT),
    DATA_NOT_FOUND("DATA_NOT_FOUND", HttpStatus.UNPROCESSABLE_ENTITY),
    ILLEGAL_OPERATION("ILLEGAL_OPERATION", HttpStatus.UNPROCESSABLE_ENTITY),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR("VALIDATION_ERROR", HttpStatus.UNPROCESSABLE_ENTITY),
    WRONG_REQUEST("WRONG_REQUEST", HttpStatus.BAD_REQUEST);

    private final String name;
    private final HttpStatus status;

    ErrorType(String name, HttpStatus status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

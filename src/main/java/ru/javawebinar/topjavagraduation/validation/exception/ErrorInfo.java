package ru.javawebinar.topjavagraduation.validation.exception;

public class ErrorInfo {
    private final ErrorType errorType;
    private final String message;
    private final String[] details;

    public ErrorInfo(ErrorType errorType, String message, String... details) {
        this.errorType = errorType;
        this.message = message;
        this.details = details;
    }
}

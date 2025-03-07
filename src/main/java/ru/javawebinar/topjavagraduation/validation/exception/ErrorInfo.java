package ru.javawebinar.topjavagraduation.validation.exception;

public class ErrorInfo {
    private ErrorType errorType;
    private String message;
    private String[] details;

    public ErrorInfo() {
    }

    public ErrorInfo(ErrorType errorType, String message, String... details) {
        this.errorType = errorType;
        this.message = message;
        this.details = details;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getDetails() {
        return details;
    }

    public void setDetails(String[] details) {
        this.details = details;
    }
}

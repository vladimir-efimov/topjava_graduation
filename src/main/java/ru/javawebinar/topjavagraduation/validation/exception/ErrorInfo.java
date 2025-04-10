package ru.javawebinar.topjavagraduation.validation.exception;

import java.util.Arrays;

public class ErrorInfo {
    private String errorName;
    private String message;
    private String[] details;

    public ErrorInfo() {
    }

    public ErrorInfo(ErrorType errorCode, String... details) {
        this.errorName = errorCode.getName();
        this.message = errorCode.getMessage();
        this.details = details;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
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

    @Override
    public String toString() {
        var detailsStr = new StringBuilder();
        Arrays.stream(details).forEach(s -> {
            String separator = detailsStr.isEmpty() ? "" : "; ";
            detailsStr.append(separator + s);
        });
        return "ErrorInfo {" +
                "errorName=" + errorName +
                ", message=" + message +
                ", details=[" + detailsStr +
                "]}";
    }
}

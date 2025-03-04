package ru.javawebinar.topjavagraduation.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Illegal operation")
public class IllegalOperationException extends RuntimeException {

    public IllegalOperationException(String message) {
        super(message);
    }
}

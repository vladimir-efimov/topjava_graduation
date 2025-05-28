package ru.javawebinar.topjavagraduation.web.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.javawebinar.topjavagraduation.validation.ErrorInfo;
import ru.javawebinar.topjavagraduation.validation.ErrorType;

import static ru.javawebinar.topjavagraduation.web.handlers.ExceptionInfoHandler.logAndGetErrorInfo;


@ControllerAdvice
public class NoHandlerExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorInfo> wrongRequest(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, ErrorType.WRONG_REQUEST, "Handler for provided address is not found");
    }
}

package ru.javawebinar.topjavagraduation.web.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.javawebinar.topjavagraduation.validation.ErrorInfo;
import ru.javawebinar.topjavagraduation.validation.ErrorType;

import static ru.javawebinar.topjavagraduation.web.handlers.ExceptionInfoHandler.logAndGetErrorInfo;


@ControllerAdvice
public class NoHandlerExceptionHandler {

    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ErrorInfo> wrongRequest(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, ErrorType.DATA_NOT_FOUND, "Handler for provided address is not found");
    }
}

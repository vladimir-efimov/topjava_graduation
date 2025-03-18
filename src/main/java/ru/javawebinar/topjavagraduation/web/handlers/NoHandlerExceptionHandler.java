package ru.javawebinar.topjavagraduation.web.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.javawebinar.topjavagraduation.validation.exception.ErrorInfo;
import ru.javawebinar.topjavagraduation.validation.exception.ErrorType;

import static ru.javawebinar.topjavagraduation.web.handlers.ExceptionInfoHandler.getErrorInfo;


@ControllerAdvice
public class NoHandlerExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorInfo> wrongRequest(HttpServletRequest req, Exception e) {
        return getErrorInfo(ErrorType.WRONG_REQUEST, e, "Handler for provided address is not found");
    }

}

package ru.javawebinar.topjavagraduation.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.javawebinar.topjavagraduation.validation.DataConflictMessageSource;
import ru.javawebinar.topjavagraduation.validation.exception.ErrorInfo;
import ru.javawebinar.topjavagraduation.validation.exception.ErrorType;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 7)
public class ExceptionInfoHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> notFoundError(HttpServletRequest req, Exception e) {
        return getErrorInfo(ErrorType.DATA_NOT_FOUND, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> bindingError(HttpServletRequest req, Exception e) {
        return getErrorInfo(ErrorType.VALIDATION_ERROR, e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> illegalArgumentError(HttpServletRequest req, Exception e) {
        return getErrorInfo(ErrorType.VALIDATION_ERROR, e);
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ErrorInfo> illegalOperationError(HttpServletRequest req, Exception e) {
        return getErrorInfo(ErrorType.ILLEGAL_OPERATION, e);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return getErrorInfo(ErrorType.DATA_CONFLICT_ERROR, e, DataConflictMessageSource.getMessage(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> internalError(HttpServletRequest req, Exception e) {
        return getErrorInfo(ErrorType.INTERNAL_SERVER_ERROR, e);
    }

    private ResponseEntity<ErrorInfo> getErrorInfo(ErrorType errorType, Exception e, String ...details) {
        return ResponseEntity.status(errorType.getStatus())
                .body(new ErrorInfo(errorType, e.getMessage(), details));
    }
}

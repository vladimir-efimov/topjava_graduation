package ru.javawebinar.topjavagraduation.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.validation.BindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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
        return getErrorInfo(ErrorType.DATA_NOT_FOUND, e, e.getMessage());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ErrorInfo> requestError(HttpServletRequest req, Exception e) {
        return getErrorInfo(ErrorType.WRONG_REQUEST, e, e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorInfo> bindingError(HttpServletRequest req, BindException e) {
        String[] details = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "'" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                .toArray(String[]::new);

        return getErrorInfo(ErrorType.VALIDATION_ERROR, e, details);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorInfo> illegalArgumentError(HttpServletRequest req, Exception e) {
        return getErrorInfo(ErrorType.VALIDATION_ERROR, e, e.getMessage());
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ErrorInfo> illegalOperationError(HttpServletRequest req, Exception e) {
        return getErrorInfo(ErrorType.ILLEGAL_OPERATION, e, e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return getErrorInfo(ErrorType.DATA_CONFLICT_ERROR, e, DataConflictMessageSource.getMessage(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> internalError(HttpServletRequest req, Exception e) {
        return getErrorInfo(ErrorType.INTERNAL_SERVER_ERROR, e, e.getMessage());
    }

    static ResponseEntity<ErrorInfo> getErrorInfo(ErrorType errorType, Exception e, String ...details) {
        return ResponseEntity.status(errorType.getStatus())
                .body(new ErrorInfo(errorType, details));
    }
}

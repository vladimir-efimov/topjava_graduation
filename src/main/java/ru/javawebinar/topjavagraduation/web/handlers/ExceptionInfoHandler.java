package ru.javawebinar.topjavagraduation.web.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.Ordered;
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

import static org.slf4j.LoggerFactory.getLogger;


@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 7)
public class ExceptionInfoHandler {

    private static final Logger log = getLogger(ExceptionInfoHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> notFoundError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, ErrorType.DATA_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ErrorInfo> requestError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, ErrorType.WRONG_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorInfo> bindingError(HttpServletRequest req, BindException e) {
        String[] details = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "'" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                .toArray(String[]::new);

        return logAndGetErrorInfo(req, ErrorType.VALIDATION_ERROR, details);
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorInfo> illegalArgumentError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, ErrorType.VALIDATION_ERROR, e.getMessage());
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ErrorInfo> illegalOperationError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, ErrorType.ILLEGAL_OPERATION, e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorInfo> conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, ErrorType.DATA_CONFLICT_ERROR, DataConflictMessageSource.getMessage(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> internalError(HttpServletRequest req, Exception e) {
        var response = logAndGetErrorInfo(req, ErrorType.INTERNAL_SERVER_ERROR, e.getMessage());
        logStackTrace(e);
        return response;
    }

    static ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req, ErrorType errorType, String ...details) {
        var errorInfo = new ErrorInfo(errorType, details);
        log.debug("Request " + req.getRequestURL().toString() +" from " + req.getRemoteHost() +
                " caused error: " + errorInfo);
        return ResponseEntity.status(errorType.getStatus()).body(errorInfo);
    }

    static void logStackTrace(Exception e) {
        Throwable t = NestedExceptionUtils.getRootCause(e);
        if (t == null) {
            t = e;
        }
        log.error("INTERNAL SERVER ERROR details: \n", t);
    }

}

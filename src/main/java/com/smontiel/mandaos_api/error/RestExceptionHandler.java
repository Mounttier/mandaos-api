package com.smontiel.mandaos_api.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Salvador Montiel on 25/mar/2018.
 */
//@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex) {
        Throwable throwable = ex.getCause() != null ? ex.getCause() : ex;
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, throwable);
        return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FieldCollisionException.class)
    protected ResponseEntity<ApiError> handleIllegalArgument(FieldCollisionException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex);
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClassCastException.class)
    protected ResponseEntity<ApiError> handleClassCast(ClassCastException ex) {
        ex.printStackTrace(System.out);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

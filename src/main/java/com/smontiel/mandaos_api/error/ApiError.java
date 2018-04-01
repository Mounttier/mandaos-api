package com.smontiel.mandaos_api.error;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * Created by Salvador Montiel on 25/mar/2018.
 */
public class ApiError {
    private int status;
    private HttpStatus error;
    private ZonedDateTime timestamp;
    private String message;

    private ApiError() {
        timestamp = ZonedDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this(status, "Unexpected error");

    }

    public ApiError(HttpStatus status, Throwable throwable) {
        this(status, throwable.getMessage());
    }

    public ApiError(HttpStatus status, String message) {
        this();
        this.status = status.value();
        this.error = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public HttpStatus getError() {
        return error;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}

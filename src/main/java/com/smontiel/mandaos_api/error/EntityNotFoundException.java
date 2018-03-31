package com.smontiel.mandaos_api.error;

/**
 * Created by Salvador Montiel on 25/mar/2018.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public EntityNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

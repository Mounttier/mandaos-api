package com.smontiel.mandaos_api.error;

/**
 * Created by Salvador Montiel on 30/mar/2018.
 */
public class FieldCollisionException extends RuntimeException {

    public FieldCollisionException(String message) {
        super(message);
    }

    public FieldCollisionException(Throwable throwable) {
        super(throwable);
    }

    public FieldCollisionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

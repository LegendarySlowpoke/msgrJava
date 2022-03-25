package com.msgrJava.exceptions.registrationExceptions;

public class UserAlreadyExistsException extends Throwable {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

package com.msgrJava.exceptions.userExceptions;

public class UserAlreadyExistsException extends Throwable {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

package com.msgrJava.exceptions.userExceptions;

public class UserAlreadyExistsError extends Throwable {

    public UserAlreadyExistsError(String message) {
        super(message);
    }
}

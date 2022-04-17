package com.msgrJava.exceptions.userExceptions;

public class UserNotFoundError extends Throwable {
    public UserNotFoundError(String message) {
        super(message);
    }
}

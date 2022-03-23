package com.msgrJava.exceptions.clientSide;

public class UserAlreadyExistsException extends Throwable {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

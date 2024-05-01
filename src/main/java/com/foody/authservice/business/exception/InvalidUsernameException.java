package com.foody.authservice.business.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUsernameException extends ResponseStatusException {
    public InvalidUsernameException() {
        super(HttpStatus.BAD_REQUEST, "The username is already used by another user");
    }
}
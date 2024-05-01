package com.foody.authservice.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFound extends ResponseStatusException {
    public UserNotFound() {
        super(HttpStatus.NOT_FOUND, "INVALID_USER_ID");
    }
}

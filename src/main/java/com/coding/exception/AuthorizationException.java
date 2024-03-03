package com.coding.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationException  extends ServiceException{

    public AuthorizationException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super("AUTHZ_FAILED", "Access Denied", HttpStatus.UNAUTHORIZED);
    }
}

package com.coding.exception;

import org.springframework.http.HttpStatus;

// Custom exception class for authorization-related errors
public class AuthorizationException  extends ServiceException{

    public AuthorizationException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super(errorCode, errorMessage, HttpStatus.UNAUTHORIZED);
    }
}

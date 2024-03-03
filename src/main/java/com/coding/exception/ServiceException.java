package com.coding.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    public ServiceException(final String errorCode, final String errorMessage, final HttpStatus httpStatus){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

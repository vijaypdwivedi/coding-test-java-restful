package com.coding.exception;

public class ErrorDto {
    private String errorCode;
    private String errorMessage;

    public ErrorDto(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

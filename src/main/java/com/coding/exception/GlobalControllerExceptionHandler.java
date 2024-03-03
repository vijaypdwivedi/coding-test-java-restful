package com.coding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handleAutherizationException(AuthorizationException authorizationException){
        String errorCode = authorizationException.getErrorCode();
        String errorMessage = authorizationException.getErrorMessage();
        return buildErrorResponse(List.of(new ErrorDto(errorCode,errorMessage)), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleServiceException(ServiceException serviceException){
        String errorCode = serviceException.getErrorCode();
        String errorMessage = serviceException.getErrorMessage();
        return buildErrorResponse(List.of(new ErrorDto(errorCode,errorMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> buildErrorResponse(List<ErrorDto> errorList, HttpStatus httpStatus){
        return new ResponseEntity<>(errorList,httpStatus);
    }
}

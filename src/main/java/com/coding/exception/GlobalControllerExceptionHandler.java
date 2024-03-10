package com.coding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

// Controller advice to handle exceptions globally across all controllers
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    // Exception handler for AuthorizationException
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleAutherizationException(AuthorizationException authorizationException){
        String errorCode = authorizationException.getErrorCode();
        String errorMessage = authorizationException.getErrorMessage();
        return buildErrorResponse(List.of(new ErrorDto(errorCode,errorMessage)), HttpStatus.FORBIDDEN);
    }
    // Exception handler for ServiceException
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleServiceException(ServiceException serviceException){
        String errorCode = serviceException.getErrorCode();
        String errorMessage = serviceException.getErrorMessage();
        return buildErrorResponse(List.of(new ErrorDto(errorCode,errorMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to build the error response
    private ResponseEntity<?> buildErrorResponse(List<ErrorDto> errorList, HttpStatus httpStatus){
        return new ResponseEntity<>(errorList,httpStatus); // Return ResponseEntity with the error list and HTTP status
    }
}

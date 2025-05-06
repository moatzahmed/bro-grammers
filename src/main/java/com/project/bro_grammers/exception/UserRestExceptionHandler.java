package com.project.bro_grammers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class UserRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotAllowedIdException exc) {
        ErrorResponse errorResponse=new ErrorResponse(exc.getMessage(),HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException exc) {
        ErrorResponse errorResponse = new ErrorResponse(exc.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BadRequestException exc) {
        ErrorResponse errorResponse=new ErrorResponse(exc.getMessage(),HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(IOException exc) {
        ErrorResponse errorResponse=new ErrorResponse(exc.getMessage(),HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }





}

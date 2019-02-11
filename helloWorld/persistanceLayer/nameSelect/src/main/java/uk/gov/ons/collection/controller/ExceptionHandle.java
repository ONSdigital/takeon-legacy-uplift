package uk.gov.ons.collection.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandle
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value =
            { IllegalArgumentException.class })

    protected ResponseEntity<Object> handleException(
            RuntimeException ex, WebRequest request){
        String responceReturn = "No records like that exist";
        return handleExceptionInternal(ex, responceReturn,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}


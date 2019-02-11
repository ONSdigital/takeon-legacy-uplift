package uk.gov.ons.collection.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandle
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request){
        Map<String, Object>  responseReturn = new LinkedHashMap<String, Object>();
        responseReturn.put("TimeStamp", LocalDateTime.now());
        responseReturn.put("Status",404);
        responseReturn.put("Error",ex.getMessage());
        responseReturn.put("Message","");

        return handleExceptionInternal(ex, responseReturn,
                new HttpHeaders(), HttpStatus.CONFLICT,request);
    }

}


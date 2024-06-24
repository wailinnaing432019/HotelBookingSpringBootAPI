package com.hotel_booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandling {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArguments(MethodArgumentNotValidException ex){
        Map<String,String> errorMap=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public Map<String,String> handleForbidden(HttpClientErrorException.Unauthorized exp){
        exp.printStackTrace();
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error",exp.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(IllegalStateException.class)
    public Map<String,String> handleException(Exception exp){
        exp.printStackTrace();
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error",exp.getMessage());
        return errorMap;

    }
}

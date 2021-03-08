package com.sellics.assignment.amazonsearchvolume.controller;

import com.sellics.assignment.amazonsearchvolume.dto.ErrorBody;
import com.sellics.assignment.amazonsearchvolume.exception.AmazonAutocompleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AmazonAutocompleteException.class)
    protected ResponseEntity<ErrorBody> handleBusinessException(
            AmazonAutocompleteException ex) {
        ErrorBody error = ErrorBody.builder().message(ex.getMessage()).build();
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

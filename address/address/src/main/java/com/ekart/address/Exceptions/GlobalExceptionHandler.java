package com.ekart.address.Exceptions;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@PropertySource("classpath:validationMessages.properties")
@Slf4j
public class GlobalExceptionHandler {
private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    Environment environment;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo handleValidationExceptions(MethodArgumentNotValidException ex){
        List<String> list=ex.getBindingResult()
                .getFieldErrors().stream().map(error->error.getDefaultMessage()).toList();
        logger.error(list.toString());
        return ErrorInfo.builder()
                .message(list.toString())
                .error(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}

package com.ekart.Account.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
@PropertySource("classpath:messages.properties")
public class GlobalExceptionHandler {
    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Autowired
    Environment environment;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo handleValidationExceptions(MethodArgumentNotValidException ex){
        List<String> errors=ex.getBindingResult().getFieldErrors()
                .stream().map(error->error.getDefaultMessage()).toList();
        ErrorInfo errorInfo= ErrorInfo.builder()
                .error(HttpStatus.BAD_REQUEST.toString())
                .message(errors.toString())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        logger.error("Validation failed :"+errorInfo);
        return errorInfo;
    }
    @ExceptionHandler({AccountAlreadyExistsException.class,AccountTypeException.class,AccountNotFoundException.class})
    public ErrorInfo handleAccountErrors(RuntimeException ex){
        System.out.println(environment.getProperty(ex.getMessage()));
        return ErrorInfo.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(environment.getProperty(ex.getMessage()))
                .error(HttpStatus.BAD_REQUEST.toString())
                .build();
    }

    @ExceptionHandler({AddressServiceNotFoundException.class})
    public ResponseEntity<ErrorInfo> handleServiceDownExceptions(Exception e){
        logger.error(environment.getProperty(e.getMessage()));
        return new ResponseEntity<>(ErrorInfo.builder()
                .error(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message(environment.getProperty(e.getMessage()))
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.ekart.order.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
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
@PropertySources({
        @PropertySource("classpath:messages.properties"),
        @PropertySource("classpath:validationMessages.properties")
})

public class GlocablExceptionHandler {
    @Autowired
    Environment environment;
    private static final Logger logger= LoggerFactory.getLogger(GlocablExceptionHandler.class);
    @ExceptionHandler({ProductNotAvailableException.class,OrdersNotFoundException.class})
    public ErrorInfo handleExceptions(Exception e){
        return  ErrorInfo.builder()
                .error(HttpStatus.BAD_REQUEST.toString())
                .message(environment.getProperty(e.getMessage()))
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationExceptions(MethodArgumentNotValidException ex){
        List<String> list=ex.getBindingResult().getFieldErrors()
                .stream().map(error->error.getDefaultMessage()).toList();
        logger.error(list.toString());
        return new ResponseEntity<>(
                ErrorInfo.builder()
                        .timestamp(LocalDateTime.now())
                        .message(list.toString())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.toString())
                        .build()
                ,HttpStatus.BAD_REQUEST);
    }
}

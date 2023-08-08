package com.ekart.ApiGateway.exceptions;

import com.ekart.ApiGateway.error.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@PropertySource("classpath:messages.properties")
@Slf4j
public class GlobalExceptionHandler {
    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Autowired
    Environment environment;
    @ExceptionHandler({InvalidDetailsException.class,InvalidTokenException.class})
    public ResponseEntity<ErrorInfo> handleLoginExceptions(RuntimeException e){
        logger.error("invalid details");
        return new ResponseEntity<>(ErrorInfo.builder()
                .error(HttpStatus.FORBIDDEN.toString())
                .errors("test")
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.FORBIDDEN);
    }

}

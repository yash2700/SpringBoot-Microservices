package com.ekart.cart.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@PropertySources(
        @PropertySource("classpath:validationMessages.properties")
)
@Slf4j
public class GlobalControllerAdvice {
    private static final Logger logger= LoggerFactory.getLogger(GlobalControllerAdvice.class);
    @Autowired
    Environment environment;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationExceptions(MethodArgumentNotValidException ex){
        List<String> list=ex.getBindingResult().getFieldErrors().stream()
                .map(error->error.getDefaultMessage()).toList();
        logger.error(list.toString());
        return new ResponseEntity<>(ErrorInfo.builder()
                .timestamp(LocalDateTime.now())
                .errors(list)
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.toString())
                .build()
                , HttpStatus.BAD_REQUEST);
    }
}

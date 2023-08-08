package com.ekart.product.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@PropertySources({
@PropertySource(value = "classpath:messages.properties",ignoreResourceNotFound = true),
@PropertySource(value = "classpath:validationMessages.properties",ignoreResourceNotFound = true)}
)
@Slf4j
public class GlobalExceptionHandler {
    private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    Environment environment;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleValidationExceptions(MethodArgumentNotValidException ex){
        List<String> errors=ex.getBindingResult().getFieldErrors()
                .stream().map(error->error.getDefaultMessage())
                .toList();
        logger.error("Validation failed :"+errors);
        return ErrorInfo.builder()
                .timestamp(LocalDateTime.now())
                .message(errors.toString())
                .error(HttpStatus.BAD_REQUEST.toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorInfo handleProductExceptions(RuntimeException ex){
        System.out.println(environment.getProperty(ex.getMessage()));
            ErrorInfo errorInfo=ErrorInfo.builder()
                    .error(HttpStatus.BAD_REQUEST.toString())
                    .message(environment.getProperty(ex.getMessage()))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(LocalDateTime.now())
                    .build();
            logger.error("error :"+errorInfo);
            return errorInfo;
    }
}

package com.ekart.inventory.Exceptions;

import com.ekart.inventory.Errors.ErrorInfo;
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
@PropertySources(
        @PropertySource("classpath:validationMessages.properties")
)
public class GlobalExceptionHandler {
    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Autowired
    Environment environment;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationExceptions(MethodArgumentNotValidException ex){
        List<String> errors=ex.getBindingResult().getFieldErrors()
                .stream().map(error->error.getDefaultMessage()).toList();
        logger.info("Validation Error :"+errors);
        return new ResponseEntity<>(ErrorInfo.builder()
                .errors(errors.toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .error(HttpStatus.BAD_REQUEST.toString())
                .build(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ProductServiceNotFoundException.class})
    public ResponseEntity<ErrorInfo> handleExceptions(Exception e){
        logger.error("{PRODUCT_SERVICE_NOT_FOUND}");
     return  new ResponseEntity<>(ErrorInfo.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errors("{PRODUCT_SERVICE_NOT_FOUND}")
                .error(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .build(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

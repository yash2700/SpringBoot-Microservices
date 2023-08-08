package com.ekart.cart.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {
    private LocalDateTime timestamp;
    private List<String> errors;

    private int status;
    private String error;
}

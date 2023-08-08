package com.ekart.order.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorInfo {
    private LocalDateTime timestamp;
    private String  message;

    private int status;
    private String error;

}
package com.ekart.address.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorInfo {
    private LocalDateTime timestamp;
    private String message;
    private String error;
    private int status;
}

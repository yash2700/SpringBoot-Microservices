package com.ekart.Account.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {
    private LocalDateTime timestamp;
    private String message;
    private String error;
    private int status;
}

package com.ekart.ApiGateway.error;

import jdk.jfr.DataAmount;
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
    private String  errors;

    private int status;
    private String error;
}

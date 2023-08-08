package com.ekart.inventory.Errors;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {
    private LocalDateTime timestamp;
    private String  errors;

    private int status;
    private String error;
}

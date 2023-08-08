package com.ekart.order.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntryDto {
    @NotNull(message = "{order.productIds.invalid}")
    private List<Long> productIds;
    @Email(message = "{order.emailId.invalid}")
    private String emailId;
}

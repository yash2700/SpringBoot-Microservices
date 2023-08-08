package com.ekart.address.Dto;

import com.ekart.address.entity.Address;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntryDto {
    @NotNull(message="{address.pincode.invalid}")
    @Min(100000)
    private int pincode;
    @NotEmpty(message = "{address.state.invalid}")
    private String state;
    @NotEmpty(message = "{address.phoneNumber.invalid}")
    private String phoneNumber;
    @Email(message = "{address.email.invalid}")
    private String emailId;

    public static Address mapToEntity(AddressEntryDto addressEntryDto){
        return Address.builder()
                .emailId(addressEntryDto.getEmailId())
                .phoneNumber(addressEntryDto.getPhoneNumber())
                .state(addressEntryDto.getState())
                .pincode(addressEntryDto.getPincode())
                .build();
    }
}

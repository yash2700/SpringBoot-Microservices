package com.ekart.address.Dto;

import com.ekart.address.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDto {
    private long id;
    private int pincode;
    private String state;
    private String phoneNumber;
    private String emailId;

    public static AddressResponseDto mapToReponse(Address address){
        return  AddressResponseDto.builder()
                .id(address.getId())
                .emailId(address.getEmailId())
                .state(address.getState())
                .pincode(address.getPincode())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }
}

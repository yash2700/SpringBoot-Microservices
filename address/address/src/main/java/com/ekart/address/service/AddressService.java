package com.ekart.address.service;

import com.ekart.address.Dto.AddressEntryDto;
import com.ekart.address.Dto.AddressResponseDto;

import java.util.List;

public interface AddressService {
        AddressResponseDto addAddress(AddressEntryDto addressEntryDto);
        List<AddressResponseDto> getAllAddress();
        List<AddressResponseDto> getAddressByEmailId(String emailId);
}

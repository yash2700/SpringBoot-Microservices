package com.ekart.address.serviceImpl;

import com.ekart.address.AddressRepository.AddressRepository;
import com.ekart.address.Dto.AddressEntryDto;
import com.ekart.address.Dto.AddressResponseDto;
import com.ekart.address.entity.Address;
import com.ekart.address.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private static final Logger logger= LoggerFactory.getLogger(AddressServiceImpl.class);
    @Autowired
    AddressRepository addressRepository;
    @Override
    public AddressResponseDto addAddress(AddressEntryDto addressEntryDto) {
        Address address=AddressEntryDto.mapToEntity(addressEntryDto);
        addressRepository.save(address);
        logger.info("address with id :"+address.getId()+" is saved to DB!");
        AddressResponseDto addressResponseDto=AddressResponseDto.mapToReponse(address);
        return addressResponseDto;
    }

    @Override
    public List<AddressResponseDto> getAllAddress() {
        List<Address> addresses=addressRepository.findAll();
        List<AddressResponseDto> addressResponseDtos=addresses.stream()
                .map(address -> AddressResponseDto.mapToReponse(address)).toList();
        return addressResponseDtos;
    }

    @Override
    public List<AddressResponseDto> getAddressByEmailId(String emailId) {
        List<Address> addresses=addressRepository.findByEmailId(emailId);
        if(addresses.size()>0){
            return addresses.stream().map(address -> AddressResponseDto.mapToReponse(address)).toList();
        }
        return null;
    }
}

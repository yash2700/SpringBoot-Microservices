package com.ekart.address.controller;

import com.ekart.address.Dto.AddressEntryDto;
import com.ekart.address.Dto.AddressResponseDto;
import com.ekart.address.serviceImpl.AddressServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/address")
@Slf4j
public class AddressController {
    private static final Logger logger= LoggerFactory.getLogger(AddressController.class);
    @Autowired
    AddressServiceImpl addressService;

    @PostMapping(value = "/add",consumes = "application/json",produces = "application/json")
    public ResponseEntity<AddressResponseDto> addAddress(@Valid @RequestBody AddressEntryDto addressEntryDto){
        return new ResponseEntity<>(addressService.addAddress(addressEntryDto),HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAllAddress",produces = "application/json")
    public ResponseEntity<List<AddressResponseDto>> getAllAddress(){
        return new ResponseEntity<>(addressService.getAllAddress(),HttpStatus.OK);
    }

    @GetMapping(value = "/getAddressByEmailId/{emailId}",produces = "application/json")
    public ResponseEntity<List<AddressResponseDto>> getAddressByEmailId(@PathVariable String emailId){
        return new ResponseEntity<>(addressService.getAddressByEmailId(emailId),HttpStatus.OK);
    }
}

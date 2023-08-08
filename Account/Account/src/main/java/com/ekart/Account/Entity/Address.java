package com.ekart.Account.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private long id;
    private int pincode;
    private String state;
    private String phoneNumber;
    private String emailId;

}

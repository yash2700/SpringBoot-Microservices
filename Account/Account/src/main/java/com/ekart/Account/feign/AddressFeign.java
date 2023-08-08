package com.ekart.Account.feign;

import com.ekart.Account.Entity.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;
@FeignClient(name = "addressMs",url = "http://localhost:9000/")
public interface AddressFeign {
    @RequestMapping(value = "/address/getAddressByEmailId/{emailId}")
    List<Address> getAddressByEmailId(@PathVariable("emailId") String emailId);
    @RequestMapping(value = "/address/getAllAddress")
    List<Address> getAllAddress();
}

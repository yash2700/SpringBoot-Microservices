package com.ekart.product.helper;

import com.ekart.product.Dtos.InventoryEntryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;


@Component
public class RestTemplateHelper {
    @Autowired
    RestTemplate restTemplate;

    String inventoryUrl="http://inventoryMs/inventory";

    public String deleteFromInventory(Long productId,String token){
        HttpHeaders httpHeaders=setHttpHeaders(token);
        restTemplate.exchange(inventoryUrl+"/delete/"+productId,HttpMethod.DELETE,new HttpEntity<>(httpHeaders),String.class);
        return "Success";
    }

    public String addToInventory(InventoryEntryDto inventoryEntryDto, String token) {
        HttpHeaders httpHeaders = setHttpHeaders(token);
       ResponseEntity<String> res= restTemplate.exchange(inventoryUrl + "/add", HttpMethod.POST, new HttpEntity<>(inventoryEntryDto,httpHeaders), String.class);
        return res.getBody();
    }

    public HttpHeaders setHttpHeaders(String token){
       HttpHeaders httpHeaders=new HttpHeaders();
       httpHeaders.add("AUTHORIZATION","Bearer "+token);
       return httpHeaders;
    }
}

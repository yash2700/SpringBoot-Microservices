package com.ekart.Account.helper;

import com.ekart.Account.Entity.Address;
import com.ekart.Account.feign.AddressFeign;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
@Component
@Slf4j
public class    RestTemplateHelper {
    String addressUrl="";
    private static final Logger logger= LoggerFactory.getLogger(RestTemplateHelper.class);

    @Autowired
    private DiscoveryClient client;
    @Autowired
    Environment environment;

    ObjectMapper objectMapper=new ObjectMapper();
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AddressFeign addressFeign;

    public List<Address> getAddressByEmailId(String emailId,String token){
        if(addressUrl=="" || addressUrl==null)
                setUrls();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("AUTHORIZATION","Bearer "+token);
        ResponseEntity<List> address=restTemplate.exchange(addressUrl+"/getAddressByEmailId/"+emailId,HttpMethod.GET,new HttpEntity<>(httpHeaders),List.class);
        return address.getBody();
//        return addressFeign.getAddressByEmailId(emailId);
    }
    public List<Address> getAllAddress(String token) throws JsonProcessingException {
        setUrls();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("AUTHORIZATION","Bearer "+token);
        ResponseEntity<String> address=restTemplate.exchange(addressUrl+"/getAllAddress", HttpMethod.GET,new HttpEntity<>(httpHeaders),String.class);
        JsonNode jsonNode=objectMapper.readTree(address.getBody());
        Address[] res=objectMapper.convertValue(jsonNode,Address[].class);
        return Arrays.stream(res).toList();
//        return addressFeign.getAllAddress();
    }
    public void setUrls(){
//        List<ServiceInstance> list=client.getInstances("addressMS");
//        if(list.isEmpty()){
//            logger.error(environment.getProperty(String.valueOf(Errors.ADDRESS_SERVICE_NOT_FOUND)));
//            throw new AddressServiceNotFoundException(Errors.ADDRESS_SERVICE_NOT_FOUND);
//        }
//        addressUrl=list.get(0).getUri().toString()+"/address";
        addressUrl="http://addressMS/address";
    }
}

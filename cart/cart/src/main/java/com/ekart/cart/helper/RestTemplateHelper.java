package com.ekart.cart.helper;

import com.ekart.cart.Dtos.ProductResponse;
import com.ekart.cart.Exceptions.ProductServiceNotFoundException;
import com.ekart.cart.entity.Product;
import com.ekart.cart.enums.Errors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.ekart.cart.CartConfiguration.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@PropertySource("classpath:messages.properties")
@Slf4j
@RequiredArgsConstructor
public class RestTemplateHelper {
    private static final Logger logger= LoggerFactory.getLogger(RestTemplateHelper.class);
    @Autowired
    private DiscoveryClient client;
    @Autowired
    Environment environment;
    String productUrl="";
    @Autowired
    WebClient.Builder webClientBuilder;
    ObjectMapper objectMapper=new ObjectMapper();
//    WebClient webClient=WebClient.builder().defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    public List<Product> getProductByProductId(List<Long> productsList,String token) throws JsonProcessingException {
//       setUrls();
        productUrl="http://productMS/product";
        String url=productUrl+"/getProductsByList";
        String res=  webClientBuilder.build().get().uri(url,uriBuilder -> uriBuilder.queryParam("ids",productsList).build())
                .header("Content-Type", "application/json")
                .header("AUTHORIZATION","Bearer "+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(res);
        JsonNode jsonNode=objectMapper.readTree(res);
       Product[] a=objectMapper.convertValue(jsonNode,Product[].class);

        return Arrays.stream(a).toList();
    }

    public void setUrls(){
        List<ServiceInstance> list=client.getInstances("productMS");
        if(list.isEmpty()){
            logger.error(environment.getProperty(Errors.PRODUCT_SERVICE_NOT_FOUND.toString()));
            throw new ProductServiceNotFoundException(Errors.PRODUCT_SERVICE_NOT_FOUND);
        }
        productUrl=list.get(0).getUri().toString()+"/product";
    }
}

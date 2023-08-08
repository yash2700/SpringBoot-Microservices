package com.ekart.order.Helper;

import com.ekart.order.Dto.InventoryCheckDto;
import com.ekart.order.Exceptions.InventoryServiceNotFoundException;
import com.ekart.order.Exceptions.ProductServiceNotFoundException;
import com.ekart.order.entity.Product;
import com.ekart.order.enums.Errors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@PropertySource("classpath:messages.properties")
public class RestTemplateHelper {
    private static final Logger logger= LoggerFactory.getLogger(RestTemplateHelper.class);
    @Autowired
    Environment environment;

    @Autowired
    WebClient.Builder webClientBuilder;
    @Autowired
    private DiscoveryClient client;
    String inventoryUrl="http://InventoryMS/inventory";
    String productUrl="http://productMS/product";
    ObjectMapper objectMapper=new ObjectMapper();


    public List<Long> getAvailableProducts(List<Long> productIds,String token){
        String url=inventoryUrl+"/isAvailable";
      List<Long> idsAvailable=    webClientBuilder.build().get().uri(url,uriBuilder -> uriBuilder.queryParam("productsList",productIds).build())
                .header("Content-Type", "application/json")
              .header("AUTHORIZATION","Bearer "+token)
              .retrieve()
                .bodyToMono(List.class)
                .block();
        System.out.println(idsAvailable);
                return idsAvailable;
    }
    public List<Product> getProductByProductId(List<Long> productsList,String token) throws JsonProcessingException {
        String url=productUrl+"/getProductsByList";
        String res=  webClientBuilder.build().get().uri(url,uriBuilder -> uriBuilder.queryParam("ids",productsList)
                        .build())
                .header("Content-Type", "application/json")
                .header("AUTHORIZATION","Bearer "+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(res);
        JsonNode jsonNode=objectMapper.readTree(res);
        List<Product> a=objectMapper.convertValue(jsonNode, new TypeReference<List<Product>>() {
        });

        return a;
    }

    public String order(List<Long> ids,String emailId,long orderId,String token){
        String url=inventoryUrl+"/order";
        return webClientBuilder.build().post().uri(url,uriBuilder ->
                uriBuilder.queryParam("productsList",ids)
                        .queryParam("emailId",emailId)
                        .queryParam("orderId",orderId)
                        .build()
                )
                .header("AUTHORIZATION","Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String cancelOrder(Long orderId,List<Integer> ids,String token){
    String url=inventoryUrl+"/cancelOrder";
    return webClientBuilder.build().put().uri(url,uriBuilder -> uriBuilder.queryParam("orderId",orderId)
            .queryParam("ids",ids).build())
            .header("AUTHORIZATION","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        }

//        public void setUrls(){
//        List<ServiceInstance> productInstances=client.getInstances("productMS");
//        List<ServiceInstance> inventoryInstances=client.getInstances("InventoryMS");
//        if(productInstances.isEmpty()){
//            logger.error(environment.getProperty(Errors.PRODUCT_NOT_AVAILABLE.toString()));
//            throw new ProductServiceNotFoundException(Errors.PRODUCT_SERVICE_NOT_FOUND);
//        }
//        if(inventoryInstances.isEmpty()){
//            logger.error(environment.getProperty(Errors.INVENTORY_SERVICE_NOT_FOUND.toString()));
//            throw new InventoryServiceNotFoundException(Errors.INVENTORY_SERVICE_NOT_FOUND);
//        }
//        productUrl=productInstances.get(0).getUri().toString()+"/product";
//        inventoryUrl=inventoryInstances.get(0).getUri().toString()+"/inventory";
//        }

}

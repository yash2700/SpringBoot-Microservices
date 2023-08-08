package com.ekart.inventory.RestTemplateHelper;

import com.ekart.inventory.Dtos.InventoryEntryDto;
import com.ekart.inventory.Exceptions.ProductServiceNotFoundException;
import com.ekart.inventory.enums.Error;
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
import com.ekart.inventory.enums.*;

import java.util.List;

@Component
@PropertySource("classpath:messages.properties")
public class RestTemplateHelper {
    private static final Logger logger= LoggerFactory.getLogger(RestTemplateHelper.class);
    @Autowired
    private DiscoveryClient client;
    @Autowired
    WebClient.Builder webClientbuilder;
    String productUrl="";
    @Autowired
    private Environment environment;

    public String updateProductQuantity(InventoryEntryDto inventoryEntryDto,String type,String token){
//        List<ServiceInstance> list=client.getInstances("productMS");
//        if(list.isEmpty()){
//            logger.error(environment.getProperty(Error.PRODUCT_SERVICE_NOT_FOUND.toString()));
//            throw new ProductServiceNotFoundException(Error.PRODUCT_SERVICE_NOT_FOUND);
//        }
        productUrl="http://productMS/product";
        String url=productUrl+"/updateProductQuantity";
        System.out.println(webClientbuilder.build().put()
                .uri(url,uriBuilder -> uriBuilder.queryParam("type",type).build())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AUTHORIZATION","Bearer "+token)
                .bodyValue(inventoryEntryDto)
                .retrieve()
                .bodyToMono(String.class)
                .block()
        );


        return "success";
    }
}

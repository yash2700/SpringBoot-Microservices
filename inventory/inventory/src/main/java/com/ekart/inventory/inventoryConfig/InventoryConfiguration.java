package com.ekart.inventory.inventoryConfig;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class InventoryConfiguration {
    @Bean
    @LoadBalanced
    WebClient.Builder webClientbuilder(){
        return WebClient.builder();
    }
}

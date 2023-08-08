package com.ekart.ApiGateway.config;

import com.ekart.ApiGateway.enums.Errors;
import com.ekart.ApiGateway.exceptions.InvalidDetailsException;
import com.ekart.ApiGateway.exceptions.InvalidTokenException;
import com.ekart.ApiGateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    RouteValidator routeValidator;

    @Autowired
    RestTemplate template;

    @Autowired
    JwtUtil util;

    public AuthenticationFilter(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {                ServerHttpRequest request = null;

            if(routeValidator.isSecured.test( exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new InvalidDetailsException(Errors.INVALID_DETAILS);
                }
                String authHeaders=exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeaders!=null && authHeaders.startsWith("Bearer ")){
                    authHeaders=authHeaders.substring(7);
                }
                try {
//                    template.getForObject("http://identityMS/validate?token"+authHeaders, String.class);
                    util.validateToken(authHeaders);
                     request= exchange.getRequest()
                            .mutate()
                            .header(HttpHeaders.AUTHORIZATION,"Bearer "+authHeaders).build();
                }
                catch (Exception e){
                    throw new InvalidTokenException(Errors.INVALID_TOKEN);
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        }));
    }

    public static class Config{

        }
}

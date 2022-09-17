package com.github.peaceture.microservice;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author 安宁
 */
@Configuration(proxyBeanMethods = false)
public class WebFluxClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder(ReactorLoadBalancerExchangeFilterFunction loadBalancer) {
        return WebClient.builder()
                //TODO handle http and https
                .baseUrl("//peacetrue-microservice-resource-server")
                .filter(loadBalancer);
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

}

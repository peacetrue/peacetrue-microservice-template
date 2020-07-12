package com.github.peaceture.microservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import java.net.URI;

/**
 * @author 安宁
 */
@Slf4j
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        //TODO actuator/env 认证处理
        SpringApplication.run(GatewayApplication.class, args);
    }

    /** 如果启用 https，同时启动一个 http 服务，重定向到 https */
    @Bean
    @Profile("https")
    public DisposableServer redirectToHttps() {
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter((request, response) -> {
            URI https = UriComponentsBuilder.fromUri(request.getURI())
                    .scheme("https").port(443)
                    .build().toUri();
            log.info("从定向请求[{}] -> [{}]", request.getURI(), https);
            response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
            response.getHeaders().setLocation(https);
            return response.setComplete();
        });

        return HttpServer.create().port(80).handle(adapter).bindNow();
    }
}

package com.github.peaceture.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author 安宁
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public Object index(@AuthenticationPrincipal User user) {
        return user;
    }

    @Autowired
    private WebClient webClient;

    @GetMapping("/message")
    public Mono<String> getMessage() {
        return webClient
                .get()
                .uri("/message")
                .retrieve()
                .bodyToMono(String.class);
    }

}

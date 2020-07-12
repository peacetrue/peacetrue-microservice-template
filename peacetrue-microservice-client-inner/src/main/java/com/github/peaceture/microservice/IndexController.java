package com.github.peaceture.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 安宁
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public Object index(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                        @AuthenticationPrincipal OAuth2User oauth2User) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("authorizedClient", authorizedClient);
        result.put("oauth2User", oauth2User);
        return result;
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

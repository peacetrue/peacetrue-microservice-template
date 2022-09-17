package com.github.peaceture.microservice;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CsrfWebFilter;
import org.springframework.security.web.server.util.matcher.AndServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author 安宁
 */
@Profile("!disable_security")
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf().requireCsrfProtectionMatcher(new AndServerWebExchangeMatcher(
                CsrfWebFilter.DEFAULT_CSRF_MATCHER,
                new NegatedServerWebExchangeMatcher(EndpointRequest.toAnyEndpoint())
        ));
        http.authorizeExchange(exchanges -> exchanges
                        .matchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class)).permitAll()
                        .anyExchange().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults())
        ;
        return http.build();
    }

    @Bean
    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {

        DefaultReactiveOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(
                ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode().clientCredentials().build());
        return authorizedClientManager;
    }

    @Primary
    @Bean
    public WebClient.Builder webClientBuilder(ReactiveOAuth2AuthorizedClientManager authorizedClientManager,
                                              WebClient.Builder builder) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId("client-inner-client-credentials");
        return builder.filter(oauth);
    }

}

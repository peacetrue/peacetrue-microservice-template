package com.github.peaceture.microservice;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CsrfWebFilter;
import org.springframework.security.web.server.util.matcher.AndServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Profile("security")
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf().requireCsrfProtectionMatcher(new AndServerWebExchangeMatcher(
                CsrfWebFilter.DEFAULT_CSRF_MATCHER,
                //忽略 Endpoint，否则 SBA 调用失败
                new NegatedServerWebExchangeMatcher(EndpointRequest.toAnyEndpoint())
        ));
        http.authorizeExchange(exchanges ->
                exchanges
                        .matchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class)).permitAll()
                        //TODO uaa include read by write
                        .pathMatchers(HttpMethod.GET, "/message/**")
                        .hasAnyAuthority("SCOPE_message.write", "SCOPE_message.read")
                        .pathMatchers(HttpMethod.POST, "/message/**")
                        .hasAuthority("SCOPE_message:write")
                        .anyExchange().authenticated()
        )
                .httpBasic(withDefaults())//for SBA
                .formLogin(withDefaults())//for human
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(withDefaults()))//for client
        ;
        return http.build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService(SecurityProperties properties) {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(properties.getUser().getName())
                .password(properties.getUser().getPassword())
                .roles(properties.getUser().getRoles().toArray(new String[0]))
                .build();
        return new MapReactiveUserDetailsService(user);
    }
}

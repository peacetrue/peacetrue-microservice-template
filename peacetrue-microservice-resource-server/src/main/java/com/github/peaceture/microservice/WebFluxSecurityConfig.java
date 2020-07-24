/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.peaceture.microservice;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf().requireCsrfProtectionMatcher(new NegatedServerWebExchangeMatcher(EndpointRequest.toAnyEndpoint()));

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
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(withDefaults()));
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

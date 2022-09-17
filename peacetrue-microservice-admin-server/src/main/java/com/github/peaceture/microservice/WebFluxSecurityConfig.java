package com.github.peaceture.microservice;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author 安宁
 */
@Profile("!disable_security")
@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            AdminServerProperties adminServer) {
        //copy from ReactiveManagementWebSecurityAutoConfiguration.springSecurityFilterChain
        http.authorizeExchange(exchanges -> exchanges
                .matchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class)).permitAll()
                .pathMatchers(adminServer.path("/assets/**")).permitAll()
                .pathMatchers(adminServer.path("/login")).permitAll()
                .anyExchange().authenticated()
        );
        http.formLogin()
                .loginPage(adminServer.path("/login"))
                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler(adminServer.path("/")));
        http.logout().logoutUrl(adminServer.path("/logout"));
        http.httpBasic(withDefaults());
        http.csrf().disable();
        //bug https://github.com/spring-projects/spring-security/issues/5766
//        http.csrf().csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
//                .requireCsrfProtectionMatcher(new AndServerWebExchangeMatcher(
//                        CsrfWebFilter.DEFAULT_CSRF_MATCHER,
//                        new NegatedServerWebExchangeMatcher(EndpointRequest.toAnyEndpoint()),
//                        new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, adminServer.path("/instances"))),
//                        new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.DELETE, adminServer.path("/instances/*"))),
//                        new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers(adminServer.path("/actuator/*"), adminServer.path("/login")))
//                ));
        return http.build();
    }

}

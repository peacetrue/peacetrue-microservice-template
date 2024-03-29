package com.github.peaceture.microservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.server.adapter.HttpWebHandlerAdapter;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.DefaultWebSessionManager;

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

    @Bean
    @Profile("https")
    public AutoLifecycleWebServer autoLifecycleWebServer(HttpHandler httpHandler) {
        return new AutoLifecycleWebServer(httpHandler);
    }

    @Autowired(required = false)
    public void configHttpHandler(HttpHandler httpHandler) {
        if (httpHandler == null) return;
        if (!(httpHandler instanceof HttpWebHandlerAdapter)) return;
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        CookieWebSessionIdResolver sessionIdResolver = new CookieWebSessionIdResolver();
        sessionIdResolver.setCookieName("GATEWAY_SESSION");
        sessionManager.setSessionIdResolver(sessionIdResolver);
        ((HttpWebHandlerAdapter) httpHandler).setSessionManager(sessionManager);
    }

//    /** 如果启用 https，同时启动一个 http 服务，重定向到 https */
//    @Bean
//    @Profile("https")
//    public DisposableServer redirectToHttps() {
//        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter((request, response) -> {
//            URI https = UriComponentsBuilder.fromUri(request.getURI())
//                    .scheme("https").port(443)
//                    .build().toUri();
//            log.info("从定向请求[{}] -> [{}]", request.getURI(), https);
//            response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
//            response.getHeaders().setLocation(https);
//            return response.setComplete();
//        });
//
//        return HttpServer.create().port(80).handle(adapter).bindNow();
//    }

}

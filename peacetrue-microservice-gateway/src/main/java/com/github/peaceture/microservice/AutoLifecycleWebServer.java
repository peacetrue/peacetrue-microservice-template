package com.github.peaceture.microservice;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.server.reactive.HttpHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

/**
 * @author : xiayx
 * @since : 2020-08-31 08:01
 **/
public class AutoLifecycleWebServer {

    private final HttpHandler httpHandler;
    private WebServer webServer;

    public AutoLifecycleWebServer(HttpHandler httpHandler) {
        this.httpHandler = Objects.requireNonNull(httpHandler);
    }

    @PostConstruct
    public void start() {
        ReactiveWebServerFactory factory = new NettyReactiveWebServerFactory(80);
        this.webServer = factory.getWebServer(httpHandler);
        this.webServer.start();
    }

    @PreDestroy
    public void stop() {
        this.webServer.stop();
    }
}

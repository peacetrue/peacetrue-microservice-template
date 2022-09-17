package com.github.peaceture.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author 安宁
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ClientInnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientInnerApplication.class, args);
    }

}

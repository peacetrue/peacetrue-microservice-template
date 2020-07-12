package com.github.peaceture.microservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

/**
 * @author : 安宁
 * @since : 2020-07-09 10:47
 **/
@SpringBootTest
class ConfigCenterApplicationTest {

    @Autowired
    private Environment environment;

    @Test
    void cannotReadRemoteRepository() {
        String property = environment.getProperty("eureka.instance.hostname", String.class);
        Assertions.assertNull(property, "配置中心不能读取远程仓库配置");
    }
}

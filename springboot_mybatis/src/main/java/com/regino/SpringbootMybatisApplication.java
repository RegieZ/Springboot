package com.regino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringbootMybatisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext beanFactory = SpringApplication.run(SpringbootMybatisApplication.class, args);
        Object user = beanFactory.getBean("user");
        System.out.println(user);
    }

    // 在启动类中将restTemplate注入到spring容器
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

package com.regino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringcloudConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudConsumerServiceApplication.class, args);
    }

    //注册RestTemplate
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}

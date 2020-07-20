package com.regino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
/*
    共同点：都可以声明是一个客户端
    @EnableDiscoveryClient：eureka/zk
    @EnableEurekaClient: 只针对eureka客户端生效
 */
@EnableEurekaClient //声明是一个客户端
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

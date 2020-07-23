package com.regino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients //开启feign支持
public class SpringcloudConsumerServiceFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudConsumerServiceFeignApplication.class, args);
    }

    //注册RestTemplate
    @Bean
    @LoadBalanced //开启负载均衡 ---> 启动Ribbon
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}

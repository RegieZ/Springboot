package com.regino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*@SpringBootApplication
*//*
    共同点：都可以声明是一个客户端
    @EnableDiscoveryClient：eureka/zk
    @EnableEurekaClient: 只针对eureka客户端生效
 *//*
@EnableEurekaClient //声明是一个客户端
@EnableCircuitBreaker //开启熔断器*/
@SpringCloudApplication
@EnableHystrixDashboard //开启仪表盘监控，- http://localhost:8081/hystrix -填写 http://localhost:8081/actuator/hystrix.stream
public class SpringcloudConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudConsumerServiceApplication.class, args);
    }

    //注册RestTemplate
    @Bean
    @LoadBalanced //开启负载均衡 ---> 启动Ribbon
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}

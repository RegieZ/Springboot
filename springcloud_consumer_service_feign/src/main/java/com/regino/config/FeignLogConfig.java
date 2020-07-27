package com.regino.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //声明是一个配置类
public class FeignLogConfig {

    /*
        声明feign日志级别
     */
    @Bean
    public Logger.Level configLog(){
        return Logger.Level.FULL;//None, Basic, Headers, Full
    }
}

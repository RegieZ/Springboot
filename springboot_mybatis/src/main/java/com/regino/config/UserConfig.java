package com.regino.config;

import com.regino.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    @Conditional(MyCondition.class)
    public User user() {
        User user = new User();
        user.setId(23);
        user.setUname("Regino");
        return user;
    }
}

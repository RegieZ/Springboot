package com.regino.config;

import com.regino.pojo.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    /*
        原生注解:
            @Conditional
                注解中需要添加一个实现类condition接口的类字节码
        派生注解/衍生注解
            @ConditionalOnBean(Apple.class)：
                从当前环境中查询是否存在Apple Bean,如果存在则返回true,否则false
            @ConditionalOnMissingBean(Apple.class):
                从当前环境中查询是否存在Apple Bean,如果存在则返回false,否则true
            @ConditionalOnProperty(name = "author", havingValue = "regino")
                从配置文件中查找key是author ,val是regino,如果满足则返回true,如果不满足则返回false

     */
    @Bean
    //@Conditional(MyCondition.class)
    //@ConditionalOnBean(MyCondition.class)
    //@ConditionalOnMissingBean(MyCondition.class)
    @ConditionalOnProperty(name = "author", havingValue = "regino")
    public User user() {
        User user = new User();
        user.setId(23);
        user.setUname("Regino");
        return user;
    }
}

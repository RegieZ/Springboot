package com.regino.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MyCondition implements Condition {

        /*
        需求：
            根据环境中是否有data-redis注入user
            如果导入了redis坐标则注入user  bean
            如果没有导入了redis坐标则不注入user  bean
     */

    @Override
    public boolean matches(ConditionContext conditionContext,
                           AnnotatedTypeMetadata annotatedTypeMetadata) {
        boolean flag = true;
        try {
            //通过反射创建redisTemplate，如果没有依赖redis坐标必然报错
            Class<?> aClass = Class.forName("org.springframework.data.redis.core.RedisTemplate");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}

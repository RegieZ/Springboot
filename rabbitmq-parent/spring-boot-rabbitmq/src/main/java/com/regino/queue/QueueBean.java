package com.regino.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueBean {

    //简单模式
    @Bean
    public Queue queue(){
        return new Queue("simple_queue");
    }

    //发布订阅模式 fanout exchange，fanout A 队列
    @Bean
    public Queue fanoutA(){
        return new Queue("fanout.A");
    }

    //发布订阅模式 fanout exchange，fanout A 队列
    @Bean
    public Queue fanoutB(){
        return new Queue("fanout.B");
    }

    //创建 fanout 交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout_ex");
    }

    //将fanout.A队列完成与fanout_ex交换机的绑定
    @Bean
    Binding bindingFanoutA(FanoutExchange fanoutExchange, Queue fanoutA){//参数前后壳换
        return BindingBuilder.bind(fanoutA).to(fanoutExchange);
    }

    //将fanout.B队列完成与fanout_ex交换机的绑定
    @Bean
    Binding bindingFanoutB(FanoutExchange fanoutExchange, Queue fanoutB){//参数前后壳换
        return BindingBuilder.bind(fanoutB).to(fanoutExchange);
    }
}

package com.regino.queue;

import org.springframework.amqp.core.*;
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

    //发布订阅模式 direct exchange，direct A 队列
    @Bean
    public Queue directA(){
        return new Queue("direct.A");
    }

    //发布订阅模式 direct exchange，direct A 队列
    @Bean
    public Queue directB(){
        return new Queue("direct.B");
    }

    //创建direct exchange
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct_ex");
    }

    //绑定
    @Bean
    Binding bindingDirectA(DirectExchange directExchange, Queue directA){//参数前后壳换
        return BindingBuilder.bind(directA).to(directExchange).with("One");
    }

    //绑定
    @Bean
    Binding bindingDirectB(DirectExchange directExchange, Queue directB){//参数前后壳换
        return BindingBuilder.bind(directB).to(directExchange).with("Two");
    }
}

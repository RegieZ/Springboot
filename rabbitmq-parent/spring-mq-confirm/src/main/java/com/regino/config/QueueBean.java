package com.regino.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueBean {

    /*
        参数1：队列名称
        参数2：是否持久化，默认true
        参数3：是否排外，排除其他消费者，默认false，如果为true则只会允许一个消费者队列中的消息
        参数4：是否自动删除，默认false
     */
    @Bean
    public Queue orderA() {
        return new Queue("orderA", true, false, false);
    }

    /*
        交换机
        参数1：交换机名称
        参数2：是否持久化，默认true
        参数3：是否自动删除，默认false
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("order_direct_ex", true, false);
    }

    /*
        绑定队列
        复习Spring：
            根据noc容器中Bean主食的方法名去找，如果变量名相同或只有一个该方法的变量，则可以找到
            否则应加注释
            @Qualifier:
                UserService 接口
                UserServiceImpl1
                UserServiceImpl2
                指定用哪一个实现类
     */
    @Bean
    //Binding bindingOrderA(DirectExchange directExchange, Queue orderA){
    Binding bindingOrderA(@Qualifier("directExchange") DirectExchange direct, Queue orderA){
        return BindingBuilder.bind(orderA).to(direct).with("order");
    }
}
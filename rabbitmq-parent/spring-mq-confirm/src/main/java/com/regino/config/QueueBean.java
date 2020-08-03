package com.regino.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
        return new Queue("order.A", true, false, false);
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
    Binding bindingOrderA(@Qualifier("directExchange") DirectExchange direct, Queue orderA) {
        return BindingBuilder.bind(orderA).to(direct).with("order");
    }

    @Bean
    public Queue orderB() {
        Map<String, Object> map = new HashMap<>();
        //5000单位毫秒，表示5秒后过期
        map.put("x-message-tt1", 5000);
        //过期后发送到死信交换机，然后根据routingKey录入到死信队列中
        //队列一旦创建后不可以修改，使用的orderB队列做了修改，所以需要在RabbitMQ中删除orderB重新创建
        //设置死信交换机
        map.put("x-dead-letter-exchange", "order_del_ex");
        //设置routingKey
        map.put("x-dead-letter-routing-key", "order_del");
        return new Queue("order.B", true, false, false, map);
        /*
        消息幂等问题
            SELECT * FROM `account`;

            #朱某账户未处理sql
            UPDATE account SET money=money+1000 WHERE id =1;

            #乐观锁-->保证了只会执行一遍
            UPDATE account SET money=money+1000,version=version+1 WHERE id =1 AND version=1
        */
    }

    //一个交换机可以绑定多个队列
    @Bean
    //Binding bindingOrderA(DirectExchange directExchange, Queue orderA){
    Binding bindingOrderB(@Qualifier("directExchange") DirectExchange direct, Queue orderB) {
        return BindingBuilder.bind(orderB).to(direct).with("orderB"); //routingKey也是路由键
    }

    //创建死信队列
    @Bean
    public Queue delQueue() {
        return new Queue("order_del_queue");
    }

    //创建死信交换机
    @Bean
    public DirectExchange delDirectExchange() {
        return new DirectExchange("order_del_ex");
    }

    @Bean
    Binding bindingDelQueue(DirectExchange delDirectExchange, Queue delQueue) {
        return BindingBuilder.bind(delQueue).to(delDirectExchange).with("order_del");
    }
}

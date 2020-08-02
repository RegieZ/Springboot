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

    //发布订阅模式 fanout exchange，fanout B 队列
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

    //发布订阅模式 direct exchange，direct B 队列
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
        return BindingBuilder.bind(directA).to(directExchange).with("one");
    }

    //绑定
    @Bean
    Binding bindingDirectB(DirectExchange directExchange, Queue directB){//参数前后壳换
        return BindingBuilder.bind(directB).to(directExchange).with("two");
    }

    //发布主题模式 topic exchange，topic A 队列
    @Bean
    public Queue topicA(){
        return new Queue("topic.A");
    }

    //发布主题模式 topic exchange，topic B 队列
    @Bean
    public Queue topicB(){
        return new Queue("topic.B");
    }

    //创建topic交换机
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topic_ex");
    }

    /**
     * 完成topic.A与交换机的绑定
     * routingKey:one.a
     * 使用*号，routingKey后只可以有一个词
     */
    @Bean
    Binding bindingTopicA(TopicExchange topicExchange, Queue topicA){
        return BindingBuilder.bind(topicA).to(topicExchange).with("one.*");
    }

    /**
     * 完成topic.B与交换机的绑定
     * routingKey:one/one.a/one.a.b
     * 使用#号，routingKey后可以有≥0个词
     */
    @Bean
    Binding bindingTopicB(TopicExchange topicExchange, Queue topicB){
        return BindingBuilder.bind(topicB).to(topicExchange).with("one.#");
    }
}

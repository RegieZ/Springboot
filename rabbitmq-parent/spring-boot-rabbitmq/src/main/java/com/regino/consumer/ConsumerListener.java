package com.regino.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component //注入spring容器中
@RabbitListener(queues = "simple_queue") //设置监听的队列（ProductController的routingKey）
public class ConsumerListener {

    @RabbitHandler //用于转换
    public void receiveMsg(String msg) {//String对应的是ProductController发的是String类型字符串（object）
        System.out.println("Consumer has received the message: " + msg);
    }
}

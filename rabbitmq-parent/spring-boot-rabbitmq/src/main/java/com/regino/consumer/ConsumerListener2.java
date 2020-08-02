package com.regino.consumer;

import com.regino.pojo.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component //注入spring容器中
@RabbitListener(queues = "simple_queue") //设置监听的队列（ProductController的routingKey）
public class ConsumerListener2 {

    @RabbitHandler //用于转换，String就转成String，对象就转成对象
    public void receiveMsg(String msg) {//String对应的是ProductController发的是String类型字符串（object）
        System.out.println("Consumer2 has received the message: " + msg);
    }

    @RabbitHandler //用于转换
    public void receiveUser(User msg) {//String对应的是ProductController发的是String类型字符串（object）
        System.out.println("Consumer2 has received the message: " + msg);
    }
}

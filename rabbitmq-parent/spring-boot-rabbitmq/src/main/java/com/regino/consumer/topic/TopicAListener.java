package com.regino.consumer.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.A")
public class TopicAListener {

    @RabbitHandler
    public void receiveMsg(String msg) {
        System.out.println("TopicAListener 接收到消息：" + msg);
    }
}

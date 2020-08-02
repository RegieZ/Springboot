package com.regino.consumer.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "direct.B")
public class DirectBListener {

    @RabbitHandler
    public void receiveMsg(String msg) {
        System.out.println("DirectBListener 接收到消息：" + msg);
    }
}

package com.regino.consumer.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "direct.A")
public class DirectAListener {

    @RabbitHandler
    public void receiveMsg(String msg) {
        System.out.println("DirectAListener 接收到消息：" + msg);
    }
}

package com.regino.consumer.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.B")
public class FanoutBListener {

    @RabbitHandler
    public void receiveMsg(String msg) {
        System.out.println("FanoutBListener 接收到消息：" + msg);
    }
}

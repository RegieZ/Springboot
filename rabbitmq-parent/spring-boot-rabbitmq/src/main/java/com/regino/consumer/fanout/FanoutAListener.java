package com.regino.consumer.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.A")
public class FanoutAListener {

    @RabbitHandler
    public void receiveMsg(String msg) {
        System.out.println("FanoutAListener 接收到消息：" + msg);
    }
}

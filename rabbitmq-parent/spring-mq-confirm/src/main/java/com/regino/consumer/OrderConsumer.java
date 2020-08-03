package com.regino.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "order.A") //指定监听队列名称
@Slf4j
public class OrderConsumer {

    @RabbitHandler //转换
    public void orderMsg(String msg) {
        log.info("下单消息: {}", msg); //{}表示占位符，多个占位符后面可以加多个逗号隔开
    }
}

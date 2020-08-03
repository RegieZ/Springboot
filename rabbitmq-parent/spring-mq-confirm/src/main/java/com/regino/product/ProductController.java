package com.regino.product;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发送消息通用方法
    //如果是其他的exchange，消息会经过exchange后丢失，不会进入queue
    @GetMapping("sendMsg/{exchange}/{routingKey}/{msg}")
    public void sendTopicMsg(@PathVariable String exchange,
                             @PathVariable String routingKey,
                             @PathVariable String msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }
}

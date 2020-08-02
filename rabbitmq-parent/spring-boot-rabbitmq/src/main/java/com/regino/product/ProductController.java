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

    @GetMapping("/sendMsg/{msg}")//接收打出来的url
    public void sendMsg(@PathVariable String msg){
        rabbitTemplate.convertAndSend("simple_queue",msg);//简单模式下，routingKey就是队列名称
    }
}

package com.regino.product;

import com.regino.pojo.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发送字符串类型消息
    @GetMapping("/sendMsg/{msg}")//接收打出来的url
    public void sendMsg(@PathVariable String msg) {
        rabbitTemplate.convertAndSend("simple_queue", msg);//简单模式下，routingKey就是队列名称
    }

    //发送对象类型消息
    @GetMapping("/sendUserMsg/{msg}")//接收打出来的url
    public void sendMsg() {
        User user = new User();
        user.setName("Reggie");
        user.setAge(18);
        rabbitTemplate.convertAndSend("simple_queue", user);//简单模式下，routingKey就是队列名称
    }

    //发送fanout广播类型消息
    @GetMapping("sendFanoutMsg/{msg}")
    public void sendFanoutMsg(@PathVariable String msg) {
        rabbitTemplate.convertAndSend("fanout_ex", "", msg);//fanout模式下无routingKey，但routingKey不为空
    }

    //发送direct消息
    @GetMapping("sendDirectMsg/{routingKey}/{msg}")
    public void sendDirectMsg(@PathVariable String routingKey, @PathVariable String msg) {
        rabbitTemplate.convertAndSend("direct_ex", routingKey, msg);
    }

    //发送topic消息
    @GetMapping("sendTopicMsg/{routingKey}/{msg}")
    public void sendTopicMsg(@PathVariable String routingKey, @PathVariable String msg) {
        rabbitTemplate.convertAndSend("topic_ex", routingKey, msg);
    }

    //发送消息通用方法
    //如果是其他的exchange，消息会经过exchange后丢失，不会进入queue
    @GetMapping("sendMsg/{exchange}/{routingKey}/{msg}")
    public void sendTopicMsg(@PathVariable String exchange,
                             @PathVariable String routingKey,
                             @PathVariable String msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }
}

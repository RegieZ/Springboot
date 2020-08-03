package com.regino.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class RabbitMqConfirmAndReturn implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    //保证生产者使用的是rabbitTemplate，防止用户自定义rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct //@PostConstruct会在Autowired后执行
    public void init(){
        //::代表方法的引用
        rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.setReturnCallback(this::returnedMessage);

        //可以自动去this类中找对应的方法
        /*rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);*/
    }

    //确认：确认消息是否到达交换机
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) { //ack=true，表示消息到达交换机
            log.info("消息到达交换机，ack: {}", ack);
        } else {
            log.info("消息未到达交换机，ack: {}", ack);
        }
    }

    //回退：确认消息是否达到队列，只有在消息没有从交换机路由至队列时此方法回调
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息从交换机路由至队列失败，replyCode: {}，replyText: {}，exchange: {}，routingKey: {}",
                replyCode, replyText, exchange, routingKey);
    }
}

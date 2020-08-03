package com.regino.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "order.A") //指定监听队列名称
@Slf4j //异步打印，不保证顺序
public class OrderConsumer {

    /*
        channel：信道，通过此对象可以手动确认消息
        deliveryTag：信道中标识消息索引位置
        redeliveryTag：标识消息是否重回队列，如果消息重回队列返回true
     */
    @RabbitHandler //转换
    public void orderMsg(String msg,
                         Channel channel,
                         @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                         @Header(AmqpHeaders.REDELIVERED) boolean redeliveryTag) {
        //自动确认：队列中的消息消费失败后再次回到队列，造成反复抛出异常
        try { //消息消费成功
            log.info("deliveryTag: {}, rediliveryTag: {}", deliveryTag, redeliveryTag);
            if (msg.contains("reggie")) {
                throw new RuntimeException("NoAuthorName");
            }
            log.info("下单消息: {}", msg); //{}表示占位符，多个占位符后面可以加多个逗号隔开
            //调用ack发送消息确认信息将消息从队列中移除，multiple代表是否批量删除
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {//消息消费失败
            if (redeliveryTag) {
                try {
                    log.info("消费重回过队列，且再次消费失败，将其移除");
                    channel.basicNack(deliveryTag, false, false);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                try {
                    //调用basicNack，参数3（requeue）标识是否重回队列，
                    channel.basicNack(deliveryTag, false, true);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}

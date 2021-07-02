package com.example.helper;

import com.example.service.BusinessService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 死信队列配置
 *
 * 直接@Component注解创建单个消费者实例
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/7/2 15:13
 */
@Component
public class DeadLetterReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费队列
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.deadLetterQueue.name}")
    public void receive(String text, Channel channel, Message message) throws Exception {
        System.out.println(" [DeadLetter] Received '" + text + "'");
        System.out.println(new String(message.getBody()));
        // 给业务类处理
        if (businessService.handle(text)) {
            // 处理成功消息ACK确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            // 处理失败消息拒绝，true则重新入队列，false丢弃或者进入死信队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            // 与basicReject区别就是同时支持多个消息
            // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

}

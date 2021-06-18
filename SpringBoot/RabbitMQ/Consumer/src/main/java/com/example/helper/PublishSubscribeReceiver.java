package com.example.helper;

import com.example.service.BusinessService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发布订阅模式三个消费队列消费
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 15:13
 */
@Component
public class PublishSubscribeReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费队列1
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.publishSubscribe.queue1}")
    public void receive1(String message) {
        System.out.println(" [PublishSubscribe] Received1 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费队列2
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.publishSubscribe.queue2}")
    public void receive2(String message) {
        System.out.println(" [PublishSubscribe] Received2 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费队列3
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.publishSubscribe.queue3}")
    public void receive3(String message) {
        System.out.println(" [PublishSubscribe] Received3 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

}

package com.example.helper;

import com.example.service.BusinessService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 简单队列消费
 *
 * 一对一，一个生产者，一个消费者
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 15:13
 */
@Component
public class SimpleQueueReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.simpleQueue.name}")
    public void receive(String message) {
        System.out.println(" [SimpleQueue] Received '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

}

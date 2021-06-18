package com.example.helper;

import com.example.service.BusinessService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工作队列消费
 *
 * 一对多，一个生产者，多个消费者竞争消费消息
 *
 * 直接@Component注解创建单个消费者实例
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 15:13
 */
@Component
public class WorkQueueReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费者1
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.workQueue.name}")
    public void receive(String message) {
        System.out.println(" [WorkQueue] Received1 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费者2
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.workQueue.name}")
    public void receive2(String message) {
        System.out.println(" [WorkQueue] Received2 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费者3
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.workQueue.name}")
    public void receive3(String message) {
        System.out.println(" [WorkQueue] Received3 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

}

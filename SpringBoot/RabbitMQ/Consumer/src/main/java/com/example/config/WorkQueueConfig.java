package com.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 2. 工作队列模式配置
 *
 * 一对多，一个生产者，多个消费者竞争消费消息
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 14:22
 */
@Configuration
public class WorkQueueConfig {

    /**
     * 工作队列名称
     */
    @Value("${rabbitmq.workQueue.name}")
    private String workQueueName;

    /**
     * 声明一个工作队列
     *
     * @return
     */
    @Bean
    public Queue workQueue() {
        return new Queue(workQueueName);
    }

}

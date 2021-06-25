package com.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 1. SimpleQueue模式配置
 *
 * 一对一，一个生产者，一个消费者
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 14:22
 */
@Configuration
public class SimpleQueueConfig {

    /**
     * SimpleQueue队列名称
     */
    @Value("${rabbitmq.simpleQueue.name}")
    private String simpleQueueName;

    /**
     * 声明一个SimpleQueue队列
     *
     * @return
     */
    @Bean
    public Queue simpleQueue() {
        return new Queue(simpleQueueName);
    }

}

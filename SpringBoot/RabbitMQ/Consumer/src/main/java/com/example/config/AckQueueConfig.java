package com.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ACK队列配置
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 14:22
 */
@Configuration
public class AckQueueConfig {

    /**
     * ACK队列名称
     */
    @Value("${rabbitmq.ackQueue.name}")
    private String ackQueue;

    /**
     * 声明一个ACK队列
     *
     * @return
     */
    @Bean
    public Queue ackQueue() {
        return new Queue(ackQueue);
    }

}

package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列配置
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/7/2 10:45
 */
@Configuration
public class DeadLetterConfig {

    /**
     * 订单过期队列名称
     */
    @Value("${rabbitmq.orderQueue.name}")
    private String orderQueue;

    /**
     * 死信队列交换机名称
     */
    @Value("${rabbitmq.deadLetterExchange.name}")
    private String deadLetterExchangeName;

    /**
     * 死信队列交换机路由键
     */
    @Value("${rabbitmq.deadLetterExchange.routingKey}")
    private String deadLetterExchangeRoutingKey;

    /**
     * 死信队列名称
     */
    @Value("${rabbitmq.deadLetterQueue.name}")
    private String deadLetterQueueName;

    /**
     * 声明一个订单过期普通队列
     *
     * @return
     */
    @Bean
    public Queue orderQueue() {
        // 配置参数
        // Map<String,Object> param = new HashMap<>(3);
        // param.put("x-dead-letter-exchange", deadLetterExchangeName);
        // 该参数可以修改该死信的路由key，不设置则使用原消息的路由key
        // param.put("x-dead-letter-routing-key", deadLetterExchangeRoutingKey);
        return QueueBuilder.durable(orderQueue)
                // 队列消息过期时间，如果消息本身也有过期时间，以短的过期时间为准
                .ttl(10000)
                // .withArguments(param)
                .deadLetterExchange(deadLetterExchangeName)
                // .deadLetterRoutingKey(deadLetterExchangeRoutingKey)
                .build();
    }

    /**
     * 声明死信队列交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange dlkExchange() {
        // 配置参数
        // Map<String,Object> param = new HashMap<>(3);
        return ExchangeBuilder.fanoutExchange(deadLetterExchangeName)
                // .withArguments(param)
                .build();
    }

    /**
     * 声明一个死信队列
     *
     * @return
     */
    @Bean
    public Queue dlkQueue() {
        // 配置参数
        // Map<String,Object> param = new HashMap<>(3);
        return QueueBuilder.durable(deadLetterQueueName)
                // .withArguments(param)
                .build();
    }

    /**
     * 死信队列交换机和死信队列绑定
     *
     * @return
     */
    @Bean
    public Binding dlkBind(FanoutExchange dlkExchange, Queue dlkQueue) {
        return BindingBuilder.bind(dlkQueue).to(dlkExchange);
    }

}

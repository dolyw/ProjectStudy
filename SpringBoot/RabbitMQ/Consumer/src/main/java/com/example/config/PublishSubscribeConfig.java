package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 3. 发布订阅模式配置
 *
 * 生产者将消息不是直接发送到队列，而是发送到交换机
 * 然后由交换机发送给两个队列，多个消费者各自监听一个队列，来消费消息
 *
 * 这种方式实现同一个消息被多个消费者消费
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 14:22
 */
@Configuration
public class PublishSubscribeConfig {

    /**
     * 交换机名称
     */
    @Value("${rabbitmq.publishSubscribe.name}")
    private String publishSubscribeName;

    /**
     * 交换机所绑定的队列1名称
     */
    @Value("${rabbitmq.publishSubscribe.queue1}")
    private String publishSubscribeQueueName1;

    /**
     * 交换机所绑定的队列2名称
     */
    @Value("${rabbitmq.publishSubscribe.queue2}")
    private String publishSubscribeQueueName2;

    /**
     * 交换机所绑定的队列2名称
     */
    @Value("${rabbitmq.publishSubscribe.queue3}")
    private String publishSubscribeQueueName3;

    /**
     * 声明一个Fanout交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange publishSubscribe() {
        return new FanoutExchange(publishSubscribeName);
    }


    /**
     * 声明一个队列1
     *
     * @return
     */
    @Bean
    public Queue publishSubscribeQueue1() {
        return new Queue(publishSubscribeQueueName1);
    }

    /**
     * 声明一个队列2
     *
     * @return
     */
    @Bean
    public Queue publishSubscribeQueue2() {
        return new Queue(publishSubscribeQueueName2);
    }

    /**
     * 声明一个队列3
     *
     * @return
     */
    @Bean
    public Queue publishSubscribeQueue3() {
        return new Queue(publishSubscribeQueueName3);
    }


    /**
     * 队列1绑定交换机
     * @param publishSubscribe
     * @param publishSubscribeQueue1
     * @return
     */
    @Bean
    public Binding binding1(FanoutExchange publishSubscribe, Queue publishSubscribeQueue1) {
        return BindingBuilder.bind(publishSubscribeQueue1).to(publishSubscribe);
    }

    /**
     * 队列2绑定交换机
     * @param publishSubscribe
     * @param publishSubscribeQueue2
     * @return
     */
    @Bean
    public Binding binding2(FanoutExchange publishSubscribe, Queue publishSubscribeQueue2) {
        return BindingBuilder.bind(publishSubscribeQueue2).to(publishSubscribe);
    }

    /**
     * 队列3绑定交换机
     * @param publishSubscribe
     * @param publishSubscribeQueue3
     * @return
     */
    @Bean
    public Binding binding3(FanoutExchange publishSubscribe, Queue publishSubscribeQueue3) {
        return BindingBuilder.bind(publishSubscribeQueue3).to(publishSubscribe);
    }

}

package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 4. Routing模式配置
 *
 * 生产者将消息不是直接发送到队列，而是发送到交换机
 * 然后由交换机匹配路由键，发送到路由键一致的所有队列
 * 多个消费者各自监听一个队列，来消费消息
 *
 * 这种方式实现同一个消息被多个消费者消费
 *
 * 交换机四种模式
 * 1. 直连交换机：Direct exchange：匹配RoutingKey路由键
 * 2. 扇形交换机：Fanout exchange：忽略RoutingKey路由键
 * 3. 主体交换机：Topic exchange：模糊匹配RoutingKey路由键
 * 4. 头部交换机：Headers exchange：忽略RoutingKey路由键，通过Headers信息匹配
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 14:22
 */
@Configuration
public class RoutingConfig {

    /**
     * 交换机名称
     */
    @Value("${rabbitmq.routing.name}")
    private String routingName;

    /**
     * 交换机所绑定的队列1名称
     */
    @Value("${rabbitmq.routing.queue1}")
    private String routingQueueName1;

    /**
     * 交换机所绑定的队列2名称
     */
    @Value("${rabbitmq.routing.queue2}")
    private String routingQueueName2;

    /**
     * 交换机所绑定的队列2名称
     */
    @Value("${rabbitmq.routing.queue3}")
    private String routingQueueName3;

    /**
     * 交换机所绑定的队列1路由键
     */
    @Value("${rabbitmq.routing.queueRoutingKey1}")
    private String queueRoutingKey1;

    /**
     * 交换机所绑定的队列2路由键
     */
    @Value("${rabbitmq.routing.queueRoutingKey2}")
    private String queueRoutingKey2;

    /**
     * 交换机所绑定的队列3路由键
     */
    @Value("${rabbitmq.routing.queueRoutingKey3}")
    private String queueRoutingKey3;

    /**
     * 声明一个Direct交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(routingName);
    }


    /**
     * 声明一个队列1
     *
     * @return
     */
    @Bean
    public Queue routingQueue1() {
        return new Queue(routingQueueName1);
    }

    /**
     * 声明一个队列2
     *
     * @return
     */
    @Bean
    public Queue routingQueue2() {
        return new Queue(routingQueueName2);
    }

    /**
     * 声明一个队列3
     *
     * @return
     */
    @Bean
    public Queue routingQueue3() {
        return new Queue(routingQueueName3);
    }


    /**
     * 队列1绑定交换机
     * @param directExchange
     * @param routingQueue1
     * @return
     */
    @Bean
    public Binding routingBinding1(DirectExchange directExchange, Queue routingQueue1) {
        return BindingBuilder.bind(routingQueue1).to(directExchange).with(queueRoutingKey1);
    }

    /**
     * 队列2绑定交换机
     * @param directExchange
     * @param routingQueue2
     * @return
     */
    @Bean
    public Binding routingBinding2(DirectExchange directExchange, Queue routingQueue2) {
        return BindingBuilder.bind(routingQueue2).to(directExchange).with(queueRoutingKey2);
    }

    /**
     * 队列3绑定交换机
     * @param directExchange
     * @param routingQueue3
     * @return
     */
    @Bean
    public Binding routingBinding3(DirectExchange directExchange, Queue routingQueue3) {
        return BindingBuilder.bind(routingQueue3).to(directExchange).with(queueRoutingKey3);
    }

}

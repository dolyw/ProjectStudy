package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 5. Topics模式配置
 *
 * 生产者将消息不是直接发送到队列，而是发送到交换机
 * 然后由交换机匹配路由键，发送到路由键模糊匹配到的所有队列
 * 多个消费者各自监听一个队列，来消费消息
 *
 * 这种方式实现同一个消息被多个消费者消费
 *
 * 模糊匹配
 * '*'标识匹配点内一个或者多个字符，例a.*匹配a.a，a.aa，a.aaa，无法匹配a.a.a
 * '#'标识匹配所有点一个或者多个字符，例a.#匹配a.a，a.a.a，a.aa.aaa，无法匹配b.a
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
public class TopicsConfig {

    /**
     * 交换机名称
     */
    @Value("${rabbitmq.topics.name}")
    private String topicsName;

    /**
     * 交换机所绑定的队列1名称
     */
    @Value("${rabbitmq.topics.queue1}")
    private String topicsQueueName1;

    /**
     * 交换机所绑定的队列2名称
     */
    @Value("${rabbitmq.topics.queue2}")
    private String topicsQueueName2;

    /**
     * 交换机所绑定的队列2名称
     */
    @Value("${rabbitmq.topics.queue3}")
    private String topicsQueueName3;

    /**
     * 交换机所绑定的队列1路由键
     */
    @Value("${rabbitmq.topics.queueRoutingKey1}")
    private String queueRoutingKey1;

    /**
     * 交换机所绑定的队列2路由键
     */
    @Value("${rabbitmq.topics.queueRoutingKey2}")
    private String queueRoutingKey2;

    /**
     * 交换机所绑定的队列3路由键
     */
    @Value("${rabbitmq.topics.queueRoutingKey3}")
    private String queueRoutingKey3;

    /**
     * 声明一个Topic交换机
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicsName);
    }


    /**
     * 声明一个队列1
     *
     * @return
     */
    @Bean
    public Queue topicsQueue1() {
        return new Queue(topicsQueueName1);
    }

    /**
     * 声明一个队列2
     *
     * @return
     */
    @Bean
    public Queue topicsQueue2() {
        return new Queue(topicsQueueName2);
    }

    /**
     * 声明一个队列3
     *
     * @return
     */
    @Bean
    public Queue topicsQueue3() {
        return new Queue(topicsQueueName3);
    }


    /**
     * 队列1绑定交换机
     * @param topicExchange
     * @param topicsQueue1
     * @return
     */
    @Bean
    public Binding topicsBinding1(TopicExchange topicExchange, Queue topicsQueue1) {
        return BindingBuilder.bind(topicsQueue1).to(topicExchange).with(queueRoutingKey1);
    }

    /**
     * 队列2绑定交换机
     * @param topicExchange
     * @param topicsQueue2
     * @return
     */
    @Bean
    public Binding topicsBinding2(TopicExchange topicExchange, Queue topicsQueue2) {
        return BindingBuilder.bind(topicsQueue2).to(topicExchange).with(queueRoutingKey2);
    }

    /**
     * 队列3绑定交换机
     * @param topicExchange
     * @param topicsQueue3
     * @return
     */
    @Bean
    public Binding topicsBinding3(TopicExchange topicExchange, Queue topicsQueue3) {
        return BindingBuilder.bind(topicsQueue3).to(topicExchange).with(queueRoutingKey3);
    }

}

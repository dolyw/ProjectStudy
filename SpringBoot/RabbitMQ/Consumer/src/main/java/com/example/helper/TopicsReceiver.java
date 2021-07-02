package com.example.helper;

import com.example.service.BusinessService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
 * @date 2021/6/18 15:13
 */
@Component
public class TopicsReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费队列1
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.topics.queue1}")
    public void receive1(String message) {
        System.out.println(" [Topics] Received1 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费队列2
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.topics.queue2}")
    public void receive2(String text, Channel channel, Message message) throws IOException {
        System.out.println(" [Topics] Received2 '" + text + "'");
        // 给业务类处理
        // businessService.handle(text);
        if (businessService.handle(text)) {
            // 消息ACK确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * 消费队列3
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.topics.queue3}")
    public void receive3(String text, Channel channel, Message message) throws IOException {
        System.out.println(" [Topics] Received3 '" + text + "'");
        // 给业务类处理
        // businessService.handle(text);
        if (businessService.handle(text)) {
            // 消息ACK确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}

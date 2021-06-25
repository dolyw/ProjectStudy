package com.example.helper;

import com.example.service.BusinessService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
 * @date 2021/6/18 15:13
 */
@Component
public class RoutingReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费队列1
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.routing.queue1}")
    public void receive1(String message) {
        System.out.println(" [Routing] Received1 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费队列2
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.routing.queue2}")
    public void receive2(String message) {
        System.out.println(" [Routing] Received2 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费队列3
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.routing.queue3}")
    public void receive3(String message) {
        System.out.println(" [Routing] Received3 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

}

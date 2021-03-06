package com.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * MQ发送API接口
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 14:28
 */
@Api(description = "MQ发送API接口")
@RestController
@RequestMapping("/mq")
public class MqSendController {

    /**
     * SimpleQueue队列名称
     */
    @Value("${rabbitmq.simpleQueue.name}")
    private String simpleQueueName;

    /**
     * WorkQueues队列名称
     */
    @Value("${rabbitmq.workQueue.name}")
    private String workQueueName;

    /**
     * Publish/Subscribe交换机名称
     */
    @Value("${rabbitmq.publishSubscribe.name}")
    private String publishSubscribeName;

    /**
     * Routing交换机名称
     */
    @Value("${rabbitmq.routing.name}")
    private String routingName;

    /**
     * Routing交换机所绑定的队列1路由键
     */
    @Value("${rabbitmq.routing.queueRoutingKey1}")
    private String queueRoutingKey1;

    /**
     * Routing交换机所绑定的队列2路由键
     */
    @Value("${rabbitmq.routing.queueRoutingKey2}")
    private String queueRoutingKey2;

    /**
     * Routing交换机所绑定的队列3路由键
     */
    @Value("${rabbitmq.routing.queueRoutingKey3}")
    private String queueRoutingKey3;

    /**
     * Topics交换机名称
     */
    @Value("${rabbitmq.topics.name}")
    private String topicsName;

    /**
     * ACK队列名称
     */
    @Value("${rabbitmq.ackQueue.name}")
    private String ackQueueName;

    /**
     * 订单过期普通队列名称
     */
    @Value("${rabbitmq.orderQueue.name}")
    private String orderQueue;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * SimpleQueue发送MQ消息
     *
     * @param text
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/18 17:00
     */
    @ApiOperation(value="SimpleQueue发送MQ消息", notes="SimpleQueue发送MQ消息", produces="application/json")
    @PostMapping("/sendSimpleQueue")
    public String sendSimpleQueue(@RequestBody String text) {
        // 队列名，消息内容
        rabbitTemplate.convertAndSend(simpleQueueName, text);
        return text;
    }

    /**
     * WorkQueues模式发送MQ消息
     *
     * @param text
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/18 17:00
     */
    @ApiOperation(value="WorkQueues模式发送MQ消息", notes="WorkQueues模式发送MQ消息", produces="application/json")
    @PostMapping("/sendWorkQueues")
    public String sendWorkQueues(@RequestBody String text) {
        // 队列名，消息内容
        rabbitTemplate.convertAndSend(workQueueName, text);
        return text;
    }

    /**
     * Publish/Subscribe模式发送MQ消息
     *
     * @param text
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/18 17:03
     */
    @ApiOperation(value="Publish/Subscribe模式发送MQ消息", notes="Publish/Subscribe模式发送MQ消息", produces="application/json")
    @PostMapping("/sendPublishSubscribe")
    public String sendPublishSubscribe(@RequestBody String text) {
        // 交换机名，消息内容
        rabbitTemplate.convertAndSend(publishSubscribeName, "", text);
        return text;
    }

    /**
     * Routing模式发送MQ消息
     *
     * @param text
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/22 15:09
     */
    @ApiOperation(value="Routing模式发送MQ消息", notes="Routing模式发送MQ消息", produces="application/json")
    @PostMapping("/sendRouting")
    public String sendRouting(@RequestBody String text) {
        // 交换机名，消息内容
        // rabbitTemplate.convertAndSend(routingName, queueRoutingKey1, text);
        rabbitTemplate.convertAndSend(routingName, queueRoutingKey2, text);
        // rabbitTemplate.convertAndSend(routingName, queueRoutingKey3, text);
        return text;
    }

    /**
     * Topics模式发送MQ消息
     *
     * RoutingKey以点为分割单位
     * '*'标识匹配点内一个或者多个字符，例a.*匹配a.a，a.aa，a.aaa，无法匹配a.a.a
     * '#'标识匹配所有点一个或者多个字符，例a.#匹配a.a，a.a.a，a.aa.aaa，无法匹配b.a
     *
     * @param text
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/22 15:09
     */
    @ApiOperation(value="Topics模式发送MQ消息", notes="Topics模式发送MQ消息", produces="application/json")
    @PostMapping("/sendTopics")
    public String sendTopics(@RequestBody String text) {
        // 交换机名，消息内容
        // rabbitTemplate.convertAndSend(topicsName, "", text);
        // rabbitTemplate.convertAndSend(topicsName, "a", text);
        rabbitTemplate.convertAndSend(topicsName, "a.a", text);
        return text;
    }

    /**
     * 开启Ack发送MQ消息
     *
     * @param text
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/29 17:55
     */
    @ApiOperation(value="开启Ack发送MQ消息", notes="开启Ack发送MQ消息", produces="application/json")
    @PostMapping("/sendAck")
    public String sendAck(@RequestBody String text) throws Exception {
        CorrelationData correlationData = new CorrelationData();
        // Id直接设置为消息内容
        correlationData.setId(text);
        // 发送带上correlationData
        rabbitTemplate.convertAndSend(ackQueueName, (Object) text, correlationData);
        rabbitTemplate.convertAndSend(topicsName, "a.a", text, correlationData);
        /*correlationData.setId(UUID.randomUUID().toString());
        Message message = MessageBuilder.withBody(text.getBytes())
                // 持久化设置
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                // 消息过期时间
                .setExpiration("10000")
                .setCorrelationId(correlationData.getId()).build();
        rabbitTemplate.convertAndSend(workQueueName, message, correlationData);
        rabbitTemplate.convertAndSend(topicsName, "a.a", message, correlationData);*/
        return text;
    }

    /**
     * 订单过期队列发送MQ消息
     *
     * @param text
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/7/2 16:55
     */
    @ApiOperation(value="订单过期队列发送MQ消息", notes="订单过期队列发送MQ消息", produces="application/json")
    @PostMapping("/sendOrder")
    public String sendOrder(@RequestBody String text) throws Exception {
        CorrelationData correlationData = new CorrelationData();
        // Id直接设置为消息内容
        correlationData.setId(text);
        // 发送带上correlationData
        // rabbitTemplate.convertAndSend(orderQueue, (Object) text, correlationData);
        // correlationData.setId(UUID.randomUUID().toString());
        // 队列有设置消息过期时间，且消息本身也有过期时间，以短的过期时间为准
        Message message = MessageBuilder.withBody(text.getBytes())
                // 持久化设置
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                // 消息过期时间
                .setExpiration("5000")
                .setCorrelationId(correlationData.getId())
                .build();
        rabbitTemplate.convertAndSend(orderQueue, message, correlationData);
        return text;
    }

}

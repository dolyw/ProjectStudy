package com.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 简单队列名称
     */
    @Value("${rabbitmq.simpleQueue.name}")
    private String simpleQueueName;

    /**
     * 工作队列名称
     */
    @Value("${rabbitmq.workQueue.name}")
    private String workQueueName;

    /**
     * 发布订阅交换机名称
     */
    @Value("${rabbitmq.publishSubscribe.name}")
    private String publishSubscribeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 简单队列发送消息
     *
     * @param text
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/18 17:00
     */
    @ApiOperation(value="简单队列发送消息", notes="简单队列发送消息", produces="application/json")
    @PostMapping("/sendSimple")
    public String sendSimple(@RequestBody String text) {
        // 队列名，消息内容
        rabbitTemplate.convertAndSend(simpleQueueName, text);
        return text;
    }

    /**
     * 工作队列发送消息
     *
     * @param text
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/18 17:00
     */
    @ApiOperation(value="工作队列发送消息", notes="工作队列发送消息", produces="application/json")
    @PostMapping("/sendWork")
    public String sendWork(@RequestBody String text) {
        // 队列名，消息内容
        rabbitTemplate.convertAndSend(workQueueName, text);
        return text;
    }

    /**
     * 发布订阅交换机发送消息
     *
     * @param text
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/18 17:03
     */
    @ApiOperation(value="发布订阅交换机发送消息", notes="发布订阅交换机发送消息", produces="application/json")
    @PostMapping("/sendPublishSubscribe")
    public String sendPublishSubscribe(@RequestBody String text) {
        // 发布订阅交换机名，消息内容
        rabbitTemplate.convertAndSend(publishSubscribeName, "", text);
        return text;
    }

}

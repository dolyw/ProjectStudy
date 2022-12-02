package com.example.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * MQ消息发送是否成功回调
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/29 16:43
 */
@Configuration
public class MqConfirmReturnsConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        // 指定ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
        // 指定ReturnsCallback
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * confirm回调，消息到是否到达Exchange或者Queue
     *
     * @param correlationData
     * @param b 发送成功-true，发送失败-false
     * @param s 错误原因
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/29 16:00
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println(b);
        System.out.println(s);
        // 需要在发送的时候同时传递correlationData才能获取
        // 提前使用Id和消息内容绑定在缓存或者数据库消息记录表，Id可以任意
        // 或者也可以直接将Id设置为消息体即可
        System.out.println(correlationData.toString());
        /*ReturnedMessage returnedMessage = correlationData.getReturned();
        System.out.println(returnedMessage.toString());
        System.out.println(new String(returnedMessage.getMessage().getBody()));*/
    }

    /**
     * returnedMessage回调，Exchange消息是否到达Queue
     * Exchange消息到达Queue成功，则不回调，失败才回调
     *
     * @param returnedMessage
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/29 16:02
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        System.out.println(returnedMessage.toString());
        System.out.println(new String(returnedMessage.getMessage().getBody()));
    }

}

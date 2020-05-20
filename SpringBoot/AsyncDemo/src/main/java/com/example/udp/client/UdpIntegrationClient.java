package com.example.udp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Integration发送方式
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/20 15:53
 */
@Service
public class UdpIntegrationClient {

    private final static Logger logger = LoggerFactory.getLogger(UdpIntegrationClient.class);

    @Autowired
    private UnicastSendingMessageHandler unicastSendingMessageHandler;

    public void sendMessage(String message) {
        logger.info("发送UDP: {}", message);
        unicastSendingMessageHandler.handleMessage(MessageBuilder.withPayload(message).build());
        logger.info("发送成功");
    }

}

package com.example.config.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SocketEventHandler
 * @author wliduo
 * @date 2019/4/17 13:42
 */
@Component
public class SocketEventHandler {

    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(SocketEventHandler.class);

    /**
     * socketMap保存当前长连接用户
     */
    private Map<String, UUID> uuidMap = new ConcurrentHashMap<>(16);

    /**
     * socketIOServer
     */
    private final SocketIOServer socketIOServer;

    @Autowired
    public SocketEventHandler(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    /**
     * 当客户端发起连接时调用
     * @param socketIOClient
     * @throws
     * @return void
     * @author wliduo
     * @date 2019/4/17 13:55
     */
    @OnConnect
    public void onConnect(SocketIOClient socketIOClient) {
        String userCode = socketIOClient.getHandshakeData().getSingleUrlParam("userCode");
        if (StringUtils.isNotBlank(userCode)) {
            logger.info("用户{}开启长连接通知, NettySocketSessionId: {}, NettySocketRemoteAddress: {}", userCode, socketIOClient.getSessionId().toString(), socketIOClient.getRemoteAddress().toString());
        }
    }

    /**
     * 客户端断开连接时调用，刷新客户端信息
     * @param socketIOClient
     * @throws
     * @return void
     * @author wliduo
     * @date 2019/4/17 13:56
     */
    @OnDisconnect
    public void onDisConnect(SocketIOClient socketIOClient) {
        logger.info("用户断开长连接通知, NettySocketSessionId: {}, NettySocketRemoteAddress: {}", socketIOClient.getSessionId().toString(), socketIOClient.getRemoteAddress().toString());
    }

    /**
     * 自定义一个test事件，也可以自定义其它任何名字的事件
     * @param socketIOClient
	 * @param ackRequest
     * @throws
     * @return void
     * @author wliduo
     * @date 2019/4/17 14:02
     */
    @OnEvent("test")
    public void test(SocketIOClient socketIOClient, AckRequest ackRequest) {
        String userCode = socketIOClient.getHandshakeData().getSingleUrlParam("userCode");
    }

    /**
     * 首次连接查询系统通知并且进行推送
     * @param
     * @throws
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author wliduo
     * @date 2019/4/17 16:04
     */
    public void connectNotice(String userCode) {
        Map<String, Object> result = new HashMap<>(16);

    }

    /**
     * Spring容器在销毁Bean之前关闭，避免重启项目服务端口占用问题
     * @param
     * @throws
     * @return void
     * @author wliduo
     * @date 2019/4/17 14:49
     */
    @PreDestroy
    private void preDestroy() {
        // 转移到WebClose类统一执行
        // logger.info("---------- NettySocket通知服务开始关闭 ----------");
        // socketIOServer.stop();
        // logger.info("---------- NettySocket通知服务关闭成功 ----------");
    }

}

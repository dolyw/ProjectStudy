package com.wang.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

import javax.jms.*;

/**
 * @Desc 消息的消费者（接受者）
 * @Author wang926454
 * @Date 2018/5/28 17:07
 */
public class TestJMSConsumer {
    // 默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    // 默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    // 默认连接地址
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    @Test
    public void printActiveMQConnection() {
        System.out.println(USERNAME);
        System.out.println(PASSWORD);
        System.out.println(URL);
    }

    @Test
    public void consumer() {
        // 连接工厂
        ConnectionFactory connectionFactory;
        // 连接
        Connection connection = null;
        // 会话 接受或者发送消息的线程
        Session session = null;
        // 消息的目的地
        Destination destination;
        // 消息消费者
        MessageConsumer messageConsumer = null;
        try {
            // 实例化连接工厂
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, URL);
            // 通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            // 启动连接
            connection.start();
            // 创建session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 创建一个名称为HelloWorld的消息队列
            destination = session.createQueue("HelloWorld");
            // 创建消息消费者
            messageConsumer = session.createConsumer(destination);
            // 接收消息
            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if (textMessage != null) {
                    System.out.println("接收" + textMessage.getText());
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (messageConsumer != null) {
                try {
                    messageConsumer.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }

    }
}

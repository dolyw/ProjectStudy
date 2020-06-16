package com.wang.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;

/**
 * TestAMQP
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/1/17 15:28
 */
public class TestAMQP {
    // 默认连接用户名
    private static final String USERNAME = "guest";
    // 默认连接密码
    private static final String PASSWORD = "guest";
    // 默认VIRTUAL_HOST
    private static final String VIRTUAL_HOST = "/";
    // 默认HOST
    private static final String HOST = "127.0.0.1";
    // 默认PORT
    private static final Integer PORT = 5672;
    // 默认QUEUE_NAME
    private static final String QUEUE_NAME = "TestAMQP";

    @Test
    public void printActiveMQConnection() {
        System.out.println(USERNAME);
        System.out.println(PASSWORD);
    }

    public static Connection GetRabbitConnection() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        connectionFactory.setVirtualHost(VIRTUAL_HOST);
        connectionFactory.setHost(HOST);
        connectionFactory.setPort(PORT);
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection GetRabbitConnection2() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 连接格式：amqp://userName:password@hostName:portNumber/virtualHost
        String uri = String.format("amqp://%s:%s@%s:%d%s", USERNAME, PASSWORD, HOST, PORT, VIRTUAL_HOST);
        Connection connection = null;
        try {
            connectionFactory.setUri(uri);
            connectionFactory.setVirtualHost(VIRTUAL_HOST);
            connection = connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 推送消息
     */
    public static void Publisher() {
        // 创建一个连接
        Connection conn = GetRabbitConnection();
        if (conn != null) {
            try {
                // 创建通道
                Channel channel = conn.createChannel();
                // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；
                // 参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                String content = String.format("当前时间：%s", new Date().getTime());
                // 发送内容【参数说明：参数一：交换机名称；参数二：队列名称，参数三：消息的其他属性-routing headers，
                // 此属性为MessageProperties.PERSISTENT_TEXT_PLAIN用于设置纯文本消息存储到硬盘；参数四：消息主体】
                channel.basicPublish("", QUEUE_NAME, null, content.getBytes("UTF-8"));
                System.out.println("已发送消息：" + content);
                // 关闭连接
                channel.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消费消息
     */
    public static void Consumer() {
        // 创建一个连接
        Connection conn = GetRabbitConnection2();
        if (conn != null) {
            try {
                // 创建通道
                Channel channel = conn.createChannel();
                // 声明队列【参数说明：参数一：队列名称，参数二：是否持久化；参数三：是否独占模式；
                // 参数四：消费者断开连接时是否删除队列；参数五：消息其他参数】
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                // 创建订阅器，并接受消息
                channel.basicConsume(QUEUE_NAME, false, "", new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        String routingKey = envelope.getRoutingKey(); // 队列名称
                        String contentType = properties.getContentType(); // 内容类型
                        String content = new String(body, "utf-8"); // 消息正文
                        System.out.println("消息正文：" + content);
                        // 手动确认消息【参数说明：参数一：该消息的index；参数二：是否批量应答，true批量确认小于index的消息】
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void consumer() {
        Publisher();
        Consumer();
    }
}

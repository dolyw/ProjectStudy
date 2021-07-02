# RabbitMQ的使用

RabbitMQ 是以 AMQP 协议实现的一种中间件产品，它可以支持多种操作系统，多种编程语言，几乎可以覆盖所有主流的企业级技术平台

**代码地址**

* Github: [https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/RabbitMQ](https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/RabbitMQ)
* Gitee(码云): [https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/RabbitMQ](https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/RabbitMQ)

## 1. 简单介绍

### 1.1. Queue

Queue（队列）是 RabbitMQ 的内部对象，用于存储消息

### 1.2. Exchange

首先明确一点就是生产者产生的消息并不是直接发送给消息队列 Queue 的，而是要经过 Exchange（交换器），由 Exchange 再将消息路由到一个或多个 Queue，当然这里还会对不符合路由规则的消息进行丢弃掉，这里指的是后续要谈到的 Exchange Type

**Exchange Type**

* 直连交换机：Direct exchange：匹配 RoutingKey 路由键
* 扇形交换机：Fanout exchange：忽略 RoutingKey 路由键
* 主体交换机：Topic exchange：模糊匹配 RoutingKey 路由键
* 头部交换机：Headers exchange：忽略 RoutingKey 路由键，通过 Headers 信息匹配

## 2. 工作模式

> SpringBoot下的配置使用

### 2.1. SimpleQueue

```yml
# RabbitMQ交换机及队列名称配置
rabbitmq:
  simpleQueue:
    name: simpleQueue
```

```java
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 1. SimpleQueue模式配置
 *
 * 一对一，一个生产者，一个消费者
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 14:22
 */
@Configuration
public class SimpleQueueConfig {

    /**
     * SimpleQueue队列名称
     */
    @Value("${rabbitmq.simpleQueue.name}")
    private String simpleQueueName;

    /**
     * 声明一个SimpleQueue队列
     *
     * @return
     */
    @Bean
    public Queue simpleQueue() {
        return new Queue(simpleQueueName);
    }

}
```

```java
import com.example.service.BusinessService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 1. SimpleQueue模式配置
 *
 * 一对一，一个生产者，一个消费者
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 15:13
 */
@Component
public class SimpleQueueReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.simpleQueue.name}")
    public void receive(String message) {
        System.out.println(" [SimpleQueue] Received '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

}
```

```java
/**
 * SimpleQueue队列名称
 */
@Value("${rabbitmq.simpleQueue.name}")
private String simpleQueueName;

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
```

### 2.2. WorkQueues

```yml
# RabbitMQ交换机及队列名称配置
rabbitmq:
  workQueue:
    name: workQueue
```

```java
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 2. WorkQueues模式配置
 *
 * 一对多，一个生产者，多个消费者竞争消费消息
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 14:22
 */
@Configuration
public class WorkQueuesConfig {

    /**
     * WorkQueues队列名称
     */
    @Value("${rabbitmq.workQueue.name}")
    private String workQueueName;

    /**
     * 声明一个WorkQueues队列
     *
     * @return
     */
    @Bean
    public Queue workQueue() {
        return new Queue(workQueueName);
    }

}
```

```java
import com.example.service.BusinessService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 2. WorkQueues模式配置
 *
 * 一对多，一个生产者，多个消费者竞争消费消息
 *
 * 直接@Component注解创建单个消费者实例
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 15:13
 */
@Component
public class WorkQueuesReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费者1
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.workQueue.name}")
    public void receive(String message) {
        System.out.println(" [WorkQueues] Received1 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费者2
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.workQueue.name}")
    public void receive2(String message) {
        System.out.println(" [WorkQueues] Received2 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费者3
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.workQueue.name}")
    public void receive3(String message) {
        System.out.println(" [WorkQueues] Received3 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

}
```

```java
/**
 * WorkQueues队列名称
 */
@Value("${rabbitmq.workQueue.name}")
private String workQueueName;

@Autowired
private RabbitTemplate rabbitTemplate;

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
```

### 2.3. Publish/Subscribe

```yml
# RabbitMQ交换机及队列名称配置
rabbitmq:
  publishSubscribe:
    name: publishSubscribeExchange
    queue1: publishSubscribeQueue1
    queue2: publishSubscribeQueue2
    queue3: publishSubscribeQueue3
```

```java
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 3. Publish/Subscribe模式配置
 *
 * 生产者将消息不是直接发送到队列，而是发送到交换机
 * 然后由交换机发送给多个绑定队列
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
 * @date 2021/6/18 14:22
 */
@Configuration
public class PublishSubscribeConfig {

    /**
     * 交换机名称
     */
    @Value("${rabbitmq.publishSubscribe.name}")
    private String publishSubscribeName;

    /**
     * 交换机所绑定的队列1名称
     */
    @Value("${rabbitmq.publishSubscribe.queue1}")
    private String publishSubscribeQueueName1;

    /**
     * 交换机所绑定的队列2名称
     */
    @Value("${rabbitmq.publishSubscribe.queue2}")
    private String publishSubscribeQueueName2;

    /**
     * 交换机所绑定的队列2名称
     */
    @Value("${rabbitmq.publishSubscribe.queue3}")
    private String publishSubscribeQueueName3;

    /**
     * 声明一个Fanout交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange publishSubscribe() {
        return new FanoutExchange(publishSubscribeName);
    }


    /**
     * 声明一个队列1
     *
     * @return
     */
    @Bean
    public Queue publishSubscribeQueue1() {
        return new Queue(publishSubscribeQueueName1);
    }

    /**
     * 声明一个队列2
     *
     * @return
     */
    @Bean
    public Queue publishSubscribeQueue2() {
        return new Queue(publishSubscribeQueueName2);
    }

    /**
     * 声明一个队列3
     *
     * @return
     */
    @Bean
    public Queue publishSubscribeQueue3() {
        return new Queue(publishSubscribeQueueName3);
    }


    /**
     * 队列1绑定交换机
     * @param publishSubscribe
     * @param publishSubscribeQueue1
     * @return
     */
    @Bean
    public Binding publishSubscribeBinding1(FanoutExchange publishSubscribe, Queue publishSubscribeQueue1) {
        return BindingBuilder.bind(publishSubscribeQueue1).to(publishSubscribe);
    }

    /**
     * 队列2绑定交换机
     * @param publishSubscribe
     * @param publishSubscribeQueue2
     * @return
     */
    @Bean
    public Binding publishSubscribeBinding2(FanoutExchange publishSubscribe, Queue publishSubscribeQueue2) {
        return BindingBuilder.bind(publishSubscribeQueue2).to(publishSubscribe);
    }

    /**
     * 队列3绑定交换机
     * @param publishSubscribe
     * @param publishSubscribeQueue3
     * @return
     */
    @Bean
    public Binding publishSubscribeBinding3(FanoutExchange publishSubscribe, Queue publishSubscribeQueue3) {
        return BindingBuilder.bind(publishSubscribeQueue3).to(publishSubscribe);
    }

}
```

```java
import com.example.service.BusinessService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 3. Publish/Subscribe模式配置
 *
 * 生产者将消息不是直接发送到队列，而是发送到交换机
 * 然后由交换机发送给多个绑定队列
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
public class PublishSubscribeReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费队列1
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.publishSubscribe.queue1}")
    public void receive1(String message) {
        System.out.println(" [Publish/Subscribe] Received1 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费队列2
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.publishSubscribe.queue2}")
    public void receive2(String message) {
        System.out.println(" [Publish/Subscribe] Received2 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

    /**
     * 消费队列3
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.publishSubscribe.queue3}")
    public void receive3(String message) {
        System.out.println(" [Publish/Subscribe] Received3 '" + message + "'");
        // 给业务类处理
        businessService.handle(message);
    }

}
```

```java
/**
 * Publish/Subscribe交换机名称
 */
@Value("${rabbitmq.publishSubscribe.name}")
private String publishSubscribeName;

@Autowired
private RabbitTemplate rabbitTemplate;

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
```

### 2.4. Routing

```yml
# RabbitMQ交换机及队列名称配置
rabbitmq:
  routing:
    name: routingExchange
    queue1: routingQueue1
    queueRoutingKey1:
    queue2: routingQueue2
    queueRoutingKey2: app
    queue3: routingQueue3
    queueRoutingKey3: pc
```

```java
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
 * @date 2021/6/18 14:22
 */
@Configuration
public class RoutingConfig {

    /**
     * 交换机名称
     */
    @Value("${rabbitmq.routing.name}")
    private String routingName;

    /**
     * 交换机所绑定的队列1名称
     */
    @Value("${rabbitmq.routing.queue1}")
    private String routingQueueName1;

    /**
     * 交换机所绑定的队列2名称
     */
    @Value("${rabbitmq.routing.queue2}")
    private String routingQueueName2;

    /**
     * 交换机所绑定的队列2名称
     */
    @Value("${rabbitmq.routing.queue3}")
    private String routingQueueName3;

    /**
     * 交换机所绑定的队列1路由键
     */
    @Value("${rabbitmq.routing.queueRoutingKey1}")
    private String queueRoutingKey1;

    /**
     * 交换机所绑定的队列2路由键
     */
    @Value("${rabbitmq.routing.queueRoutingKey2}")
    private String queueRoutingKey2;

    /**
     * 交换机所绑定的队列3路由键
     */
    @Value("${rabbitmq.routing.queueRoutingKey3}")
    private String queueRoutingKey3;

    /**
     * 声明一个Direct交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(routingName);
    }


    /**
     * 声明一个队列1
     *
     * @return
     */
    @Bean
    public Queue routingQueue1() {
        return new Queue(routingQueueName1);
    }

    /**
     * 声明一个队列2
     *
     * @return
     */
    @Bean
    public Queue routingQueue2() {
        return new Queue(routingQueueName2);
    }

    /**
     * 声明一个队列3
     *
     * @return
     */
    @Bean
    public Queue routingQueue3() {
        return new Queue(routingQueueName3);
    }


    /**
     * 队列1绑定交换机
     * @param directExchange
     * @param routingQueue1
     * @return
     */
    @Bean
    public Binding routingBinding1(DirectExchange directExchange, Queue routingQueue1) {
        return BindingBuilder.bind(routingQueue1).to(directExchange).with(queueRoutingKey1);
    }

    /**
     * 队列2绑定交换机
     * @param directExchange
     * @param routingQueue2
     * @return
     */
    @Bean
    public Binding routingBinding2(DirectExchange directExchange, Queue routingQueue2) {
        return BindingBuilder.bind(routingQueue2).to(directExchange).with(queueRoutingKey2);
    }

    /**
     * 队列3绑定交换机
     * @param directExchange
     * @param routingQueue3
     * @return
     */
    @Bean
    public Binding routingBinding3(DirectExchange directExchange, Queue routingQueue3) {
        return BindingBuilder.bind(routingQueue3).to(directExchange).with(queueRoutingKey3);
    }

}
```

```java
import com.example.service.BusinessService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    public void receive2(String text, Channel channel, Message message) throws IOException {
        System.out.println(" [Routing] Received2 '" + text + "'");
        // 给业务类处理
        // businessService.handle(message);
        if (businessService.handle(text)) {
            // 消息ACK确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
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
```

```java
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

@Autowired
private RabbitTemplate rabbitTemplate;

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
```

### 2.5. Topics

```yml
# RabbitMQ交换机及队列名称配置
rabbitmq:
  topics:
    name: topicsExchange
    queue1: topicsQueue1
    queueRoutingKey1:
    queue2: topicsQueue2
    # 特殊字符转义
    queueRoutingKey2: "*"
    queue3: topicsQueue3
    # 特殊字符转义
    queueRoutingKey3: "#"
```

```java
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
```

```java
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
```

```java
/**
 * Topics交换机名称
 */
@Value("${rabbitmq.topics.name}")
private String topicsName;

@Autowired
private RabbitTemplate rabbitTemplate;

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
```

### 2.6. RPC

待补充

## 3. 死信队列

* 死信队列：DLX，dead-letter-exchange
* 利用 DLX，当消息在一个队列中变成死信 (dead message) 之后，它能被重新 publish 到另一个 Exchange，这个 Exchange 就是 DLX

**消息变成死信有以下几种情况**

* 消息被拒绝(basic.reject / basic.nack)，并且 requeue = false
* 消息 TTL 过期
* 队列达到最大长度

**死信处理过程**

* DLX 也是一个正常的 Exchange，和一般的 Exchange 没有区别，它能在任何的队列上被指定，实际上就是设置某个队列的属性
* 当这个队列中有死信时，RabbitMQ 就会自动的将这个消息重新发布到设置的 Exchange 上去，进而被路由到另一个队列
* 可以监听这个队列中的消息做相应的处理

**简单实现**

```yml
# RabbitMQ交换机及队列名称配置
rabbitmq:
  orderQueue:
    name: orderQueue
  deadLetterExchange:
    name: deadLetterExchange
    routingKey:
  deadLetterQueue:
    name: deadLetter
```

```java
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列配置
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/7/2 10:45
 */
@Configuration
public class DeadLetterConfig {

    /**
     * 订单过期队列名称
     */
    @Value("${rabbitmq.orderQueue.name}")
    private String orderQueue;

    /**
     * 死信队列交换机名称
     */
    @Value("${rabbitmq.deadLetterExchange.name}")
    private String deadLetterExchangeName;

    /**
     * 死信队列交换机路由键
     */
    @Value("${rabbitmq.deadLetterExchange.routingKey}")
    private String deadLetterExchangeRoutingKey;

    /**
     * 死信队列名称
     */
    @Value("${rabbitmq.deadLetterQueue.name}")
    private String deadLetterQueueName;

    /**
     * 声明一个订单过期普通队列
     *
     * @return
     */
    @Bean
    public Queue orderQueue() {
        // 配置参数
        // Map<String,Object> param = new HashMap<>(3);
        // param.put("x-dead-letter-exchange", deadLetterExchangeName);
        // 该参数可以修改该死信的路由key，不设置则使用原消息的路由key
        // param.put("x-dead-letter-routing-key", deadLetterExchangeRoutingKey);
        return QueueBuilder.durable(orderQueue)
                // 队列消息过期时间，如果消息本身也有过期时间，以短的过期时间为准
                .ttl(10000)
                // .withArguments(param)
                .deadLetterExchange(deadLetterExchangeName)
                // .deadLetterRoutingKey(deadLetterExchangeRoutingKey)
                .build();
    }

    /**
     * 声明死信队列交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange dlkExchange() {
        // 配置参数
        // Map<String,Object> param = new HashMap<>(3);
        return ExchangeBuilder.fanoutExchange(deadLetterExchangeName)
                // .withArguments(param)
                .build();
    }

    /**
     * 声明一个死信队列
     *
     * @return
     */
    @Bean
    public Queue dlkQueue() {
        // 配置参数
        // Map<String,Object> param = new HashMap<>(3);
        return QueueBuilder.durable(deadLetterQueueName)
                // .withArguments(param)
                .build();
    }

    /**
     * 死信队列交换机和死信队列绑定
     *
     * @return
     */
    @Bean
    public Binding dlkBind(FanoutExchange dlkExchange, Queue dlkQueue) {
        return BindingBuilder.bind(dlkQueue).to(dlkExchange);
    }

}
```

```java
import com.example.service.BusinessService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 死信队列配置
 *
 * 直接@Component注解创建单个消费者实例
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/7/2 15:13
 */
@Component
public class DeadLetterReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费队列
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.deadLetterQueue.name}")
    public void receive(String text, Channel channel, Message message) throws Exception {
        System.out.println(" [DeadLetter] Received '" + text + "'");
        System.out.println(new String(message.getBody()));
        // 给业务类处理
        if (businessService.handle(text)) {
            // 处理成功消息ACK确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            // 处理失败消息拒绝，true则重新入队列，false丢弃或者进入死信队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            // 与basicReject区别就是同时支持多个消息
            // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

}
```

```java
/**
 * 订单过期普通队列名称
 */
@Value("${rabbitmq.orderQueue.name}")
private String orderQueue;

@Autowired
private RabbitTemplate rabbitTemplate;

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
```

## 4. 消息可靠

保证消息可靠

### 4.1. ConfirmReturns

生产者发送确认机制

```yml
spring:
  # RabbitMQ配置
  rabbitmq:
    virtual-host: /
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
    # 配置更新 https://blog.csdn.net/yaomingyang/article/details/108410286
    publisher-confirm-type: correlated
    publisher-returns: true

# RabbitMQ交换机及队列名称配置
rabbitmq:
  ackQueue:
      name: ackQueue
```

```java
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
        // 提前使用Id和消息内容绑定在缓存，Id可以任意
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
```

```java
/**
 * ACK队列名称
 */
@Value("${rabbitmq.ackQueue.name}")
private String ackQueueName;

@Autowired
private RabbitTemplate rabbitTemplate;

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
```

### 4.2. 手动ACK

```yml
spring:
  # RabbitMQ配置
  rabbitmq:
    virtual-host: /
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
    listener:
      direct:
        # 手动ACK
        acknowledge-mode: manual
      simple:
        # 手动ACK
        acknowledge-mode: manual

# RabbitMQ交换机及队列名称配置
rabbitmq:
  ackQueue:
      name: ackQueue
```

```java
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ACK队列配置
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 14:22
 */
@Configuration
public class AckQueueConfig {

    /**
     * ACK队列名称
     */
    @Value("${rabbitmq.ackQueue.name}")
    private String ackQueue;

    /**
     * 声明一个ACK队列
     *
     * @return
     */
    @Bean
    public Queue ackQueue() {
        return new Queue(ackQueue);
    }

}
```

```java
import com.example.service.BusinessService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ACK队列配置
 *
 * 直接@Component注解创建单个消费者实例
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 15:13
 */
@Component
public class AckQueueReceiver {

    @Autowired
    private BusinessService businessService;

    /**
     * 消费队列
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.ackQueue.name}")
    public void receive(String text, Channel channel, Message message) throws Exception {
        System.out.println(" [ACK] Received '" + text + "'");
        // 抛异常会进行重试
        // int i = 1/0;
        // 给业务类处理
        if (businessService.handle(text)) {
            // 处理成功消息ACK确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            // 处理失败消息拒绝，true则重新入队列，false丢弃或者进入死信队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            // 与basicReject区别就是同时支持多个消息
            // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

}
```

### 4.3. 消息重试

配置如下

```yml
spring:
  application:
    name: consumer
  # RabbitMQ配置
  rabbitmq:
    virtual-host: /
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
    listener:
      direct:
        acknowledge-mode: auto
        retry:
          enabled: true
          max-attempts: 3
          # 重试初始间隔时间
          initial-interval: 3000ms
          # 间隔时间乘子，间隔时间*乘子=下一次的间隔时间
          # 最大不能超过设置的最大间隔时间
          multiplier: 2
        # default-requeue-rejected: true
      simple:
        acknowledge-mode: auto
        retry:
          enabled: true
          max-attempts: 3
          # 重试初始间隔时间
          initial-interval: 3000ms
          # 间隔时间乘子，间隔时间*乘子=下一次的间隔时间
          # 最大不能超过设置的最大间隔时间
          multiplier: 2
        # default-requeue-rejected: true
```

**必须在消费代码抛出异常才会进行重试，且 ACK 是 auto 模式才会在最大次数重试完成后，丢弃消息进入死信。如果 ACK 是 manual 手动模式，代码内没进行手动代码确认，在最大次数重试完成后，消息不会被丢弃，重启应用会被重新消费**

消费代码内的的异常抛出貌似没有太大用处。因为抛出异常就算是重试也非常有可能会继续出现异常，没有意义，所以一般都调整为 manual 手动确认模式<!-- ，重试机制作用不大 -->

manual 手动确认模式需要进行 try catch 捕获异常，然后使用 channel 对消息进行确认，catch 到异常可以进行如下两种方式补偿操作

* 手动发送到指定异常处理队列，确认消息已消费
* 给 Queue 绑定死信队列，确认消息消费失败后自动转发到死信队列处理
* [RabbitMQ重试机制](https://www.cnblogs.com/ybyn/p/13691058.html)

## 5. 消息过期

目前有两种方法可以设置消息的 TTL

* 第一种方法是通过队列属性设置，队列中所有消息都有相同的过期时间
* 第二种方法是对消息本身进行单独设置，每条消息的 TTL 可以不同

如果两种方法一起使用，则消息的 TTL 以两者之间较小的那个数值为准，对于第一种设置队列属性的方法，一旦消息过期，就会从队列中抹去，而在第二种方法中，即使消息过期，也不会马上从队列中抹去，因为每条消息是否过期是在即将投递到消费者之前判定的

**为什么这两种方法处理的方式不一样**

因为第一种方法里，队列中己过期的消息肯定在队列头部，RabbitMQ 只要定期从队头开始扫描是否有过期的消息即可。而第二种方法里，每条消息的过期时间不同，**如果要删除所有过期消息势必要扫描整个队列**，所以不如等到此消息即将被消费时再判定是否过期，如果过期再进行删除即可

:::tip PS
* 消息过期可以用作一些延时任务的处理，设置对应过期时间，没有对应的消费者，时间到期进入死信队列再做实际业务处理
* 延时任务还可以借助一个延时插件实现，比用死信更简单
* [RabbitMQ实现延时消息的两种方法](https://www.cnblogs.com/javalank/p/14751624.html)
* [RabbitMQ实现延迟消息居然如此简单，整个插件就完事了！](https://blog.csdn.net/zhenghongcs/article/details/106700446)
:::

::: danger 注意
当往死信队列中发送两条不同过期时间的消息时，如果先发送的消息 A 的过期时间大于后发送的消息 B 的过期时间时，由于消息的顺序消费，消息 B 过期后并不会立即重新 publish 到死信交换机，而是会等到消息 A 过期后一起被消费

依次发送两个消息，A 先发送，过期时间 30S，消息 B 后发送，过期时间 10S，我们想要的结果应该是 10S 收到消息 B，30S 后收到消息 A，但结果并不是，而是消息 A 30S 后被成功消费，紧接着消息 B 被消费

因此当我们使用死信队列时应该注意消息的过期时间是否都是一样的，比如订单超过 30 分钟未支付修改其状态，如果当一个队列各个消息的过期时间不一致时，使用死信队列就可能达不到延时的作用。这时候我们应该使用延时插件来实现这需求
:::

## 6. 消息持久化

创建队列，交换机时设置 durable 为 true 即可。Spring 创建默认是持久化的，可以在界面看到带 D 字母标签的队列和交换机就是持久化的

消息持久化，deliveryMode=1 代表不持久化，deliveryMode=2 代表持久化，代码如下

```java
// 队列，交换机持久化
@Bean
public Queue topicsQueue() {
    // 第二个参数 durable，true，false，默认为 true
    // return new Queue(topicsQueueName, true);
    // return new Queue(topicsQueueName);
    return QueueBuilder.durable(ackQueue).build();
}

@Bean
public TopicExchange topicExchange() {
    // 第二个参数 durable，true，false，默认为 true
    // return new TopicExchange(topicsName, true);
    // return new TopicExchange(topicsName);
    // durable默认为true
    // return ExchangeBuilder.topicExchange(topicsName).durable(true).build();
    return ExchangeBuilder.topicExchange(topicsName).build();
}

// 消息持久化
CorrelationData correlationData = new CorrelationData();
correlationData.setId(UUID.randomUUID().toString());
Message message = MessageBuilder.withBody(text.getBytes())
            // 持久化设置
            .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
            .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
            // 消息过期时间
            .setExpiration("10000")
            .setCorrelationId(correlationData.getId()).build();
rabbitTemplate.convertAndSend(workQueueName, message, correlationData);
```

* 只设置队列持久化，重启之后消息会丢失
* 只设置消息的持久化，重启之后队列消失，既而消息也会丢失
* 必须设置了队列和消息的持久化之后，当服务重启的之后，消息依旧存在

**参考**

* [RabbitMQ官网 - 介绍](https://www.rabbitmq.com/getstarted.html)
* [Spring Boot：使用Rabbit MQ消息队列](https://www.cnblogs.com/xifengxiaoma/p/11121355.html)
* [RabbitMQ系列（四）RabbitMQ事务和Confirm发送方消息确认——深入解读](https://www.cnblogs.com/vipstone/p/9350075.html)
* [SpringBoot RabbitMQ ACK 机制](https://blog.csdn.net/myNameIssls/article/details/101163617)
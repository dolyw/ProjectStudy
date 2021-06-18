# RabbitMQ的使用

RabbitMQ 是以 AMQP 协议实现的一种中间件产品，它可以支持多种操作系统，多种编程语言，几乎可以覆盖所有主流的企业级技术平台

## 1. 概念

### 1.1. Message Broker

Message Broker 是一种消息验证、传输、路由的架构模式，其设计目标主要应用于下面这些场景

* 消息路由到一个或多个目的地
* 消息转化为其他的表现方式
* 执行消息的聚集、消息的分解，并将结果发送到他们的目的地，然后重新组合相应返回给消息用户
* 调用 Web 服务来检索数据
* 响应事件或错误
* 使用发布-订阅模式来提供内容或基于主题的消息路由

### 1.2. AMQP

AMQP 是 Advanced Message Queuing Protocol 的简称，它是一个面向消息中间件的开放式标准应用层协议。AMQP 定义了这些特性

* 消息方向
* 消息队列
* 消息路由（包括：点到点和发布-订阅模式）
* 可靠性
* 安全性

## 2. 使用

> SpringBoot下的配置使用

**参考**

* [RabbitMQ官网 - 介绍](https://www.rabbitmq.com/getstarted.html)
* [RabbitMQ官网 - SimpleQueue](https://www.rabbitmq.com/tutorials/tutorial-one-spring-amqp.html)
* [RabbitMQ官网 - WorkQueue](https://www.rabbitmq.com/tutorials/tutorial-two-spring-amqp.html)
* [RabbitMQ官网 - Publish/Subscribe](https://www.rabbitmq.com/tutorials/tutorial-three-spring-amqp.html)
* [RabbitMQ官网 - Routing](https://www.rabbitmq.com/tutorials/tutorial-four-spring-amqp.html)
* [RabbitMQ官网 - Topics](https://www.rabbitmq.com/tutorials/tutorial-five-spring-amqp.html)
* [RabbitMQ官网 - Remote procedure call (RPC)](https://www.rabbitmq.com/tutorials/tutorial-six-spring-amqp.html)
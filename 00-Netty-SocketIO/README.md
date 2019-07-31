# 00-Netty-SocketIO

> 目录:[https://github.com/dolyw/NettyStudy](https://github.com/dolyw/NettyStudy)

#### 完成进度

- [x] `SpringBoot 2.1.3` + `Netty-SocketIO 1.7.17`基础架子完成
- [x] `Netty-SocketIO`配置完成
- [ ] `Netty-SocketIO`完成通讯
- [ ] 待补充...

#### 软件架构

1. `SpringBoot` + `Netty-SocketIO`

#### 项目介绍

##### `SpringBoot` + `Netty-SocketIO`快速简单实现一个聊天室

`Netty-SocketIO`是一个开源的、基于`Netty`的、`Java`版的即时消息推送项目。通过`Netty-SocketIO`，我们可以轻松的实现服务端主动向客户端推送消息的场景，比如说股票价格变化、K线图、消息提醒等。它和`WebSocket`有相同的作用，只不过`Netty-SocketIO`可支持所有的浏览器

#### 基础架子搭建

创建一个`SpringBoot 2.1.3`的`Maven`项目，这块不再详细描述，添加如下`Netty-SocketIO`依赖

```xml
<netty-socketio.version>1.7.17</netty-socketio.version>
<dependency>
    <groupId>com.corundumstudio.socketio</groupId>
    <artifactId>netty-socketio</artifactId>
    <version>${netty-socketio.version}</version>
</dependency>
```

配置文件

```yml
server:
    port: 8080

spring:
    # 开启thymeleaf必须关闭404交给异常处理器处理配置
    # 不然无法访问静态资源
    thymeleaf:
        # 开发时关闭缓存不然没法看到实时页面
        cache: false
        # 启用不严格检查
        mode: LEGACYHTML5

# SocketIO配置
socketio:
    # SocketIO端口
    port: 9090
    # 连接数大小
    workCount: 100
    # 允许客户请求
    allowCustomRequests: true
    # 协议升级超时时间(毫秒)，默认10秒，HTTP握手升级为ws协议超时时间
    upgradeTimeout: 10000
    # Ping消息超时时间(毫秒)，默认60秒，这个时间间隔内没有接收到心跳消息就会发送超时事件
    pingTimeout: 60000
    # Ping消息间隔(毫秒)，默认25秒。客户端向服务器发送一条心跳消息间隔
    pingInterval: 25000
    # 设置HTTP交互最大内容长度
    maxHttpContentLength: 1048576
    # 设置最大每帧处理数据的长度，防止他人利用大数据来攻击服务器
    maxFramePayloadLength: 1048576
```

```java
@Configuration
public class SocketConfig {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SocketConfig.class);

    @Value("${socketio.port}")
    private Integer port;

    @Value("${socketio.workCount}")
    private int workCount;

    @Value("${socketio.allowCustomRequests}")
    private boolean allowCustomRequests;

    @Value("${socketio.upgradeTimeout}")
    private int upgradeTimeout;

    @Value("${socketio.pingTimeout}")
    private int pingTimeout;

    @Value("${socketio.pingInterval}")
    private int pingInterval;

    @Value("${socketio.maxFramePayloadLength}")
    private int maxFramePayloadLength;

    @Value("${socketio.maxHttpContentLength}")
    private int maxHttpContentLength;

    /**
     * SocketIOServer配置
     * @param
     * @return com.corundumstudio.socketio.SocketIOServer
     * @throws
     * @author dolyw.com
     * @date 2019/4/17 11:41
     */
    @Bean("socketIOServer")
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        // 配置端口
        config.setPort(port);
        // 开启Socket端口复用
        com.corundumstudio.socketio.SocketConfig socketConfig = new com.corundumstudio.socketio.SocketConfig();
        socketConfig.setSoLinger(0);
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        // 连接数大小
        config.setWorkerThreads(workCount);
        // 允许客户请求
        config.setAllowCustomRequests(allowCustomRequests);
        // 协议升级超时时间(毫秒)，默认10秒，HTTP握手升级为ws协议超时时间
        config.setUpgradeTimeout(upgradeTimeout);
        // Ping消息超时时间(毫秒)，默认60秒，这个时间间隔内没有接收到心跳消息就会发送超时事件
        config.setPingTimeout(pingTimeout);
        // Ping消息间隔(毫秒)，默认25秒。客户端向服务器发送一条心跳消息间隔
        config.setPingInterval(pingInterval);
        // 设置HTTP交互最大内容长度
        config.setMaxHttpContentLength(maxHttpContentLength);
        // 设置最大每帧处理数据的长度，防止他人利用大数据来攻击服务器
        config.setMaxFramePayloadLength(maxFramePayloadLength);
        return new SocketIOServer(config);
    }

    /**
     * 开启SocketIOServer注解支持
     * @param socketServer
     * @throws
     * @return com.corundumstudio.socketio.annotation.SpringAnnotationScanner
     * @author dolyw.com
     * @date 2019/7/31 18:21
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}
```

启动类

```java
/**
 * SpringBoot启动之后执行
 * @author dolyw.com
 * @date 2019/4/17 11:45
 */
@Component
@Order(1)
public class SocketServerRunner implements CommandLineRunner {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ServerRunner.class);

    /**
     * socketIOServer
     */
    private final SocketIOServer socketIOServer;

    @Autowired
    public SocketServerRunner(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void run(String... args) {
        logger.info("---------- NettySocket通知服务开始启动 ----------");
        socketIOServer.start();
        logger.info("---------- NettySocket通知服务启动成功 ----------");
    }

}
```

**这样就配置完成了，`Netty-SocketIO`封装的挺简单的**
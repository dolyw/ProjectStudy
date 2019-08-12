# 00-Netty-SocketIO

> 目录:[https://github.com/dolyw/NettyStudy](https://github.com/dolyw/NettyStudy)

#### 完成进度

- [x] `SpringBoot 2.1.3` + `Netty-SocketIO 1.7.17`基础架子完成
- [x] `Netty-SocketIO`配置完成
- [x] `Netty-SocketIO`完成通讯
- [x] ~~已完成~~

#### 软件架构

1. `SpringBoot` + `Netty-SocketIO`

#### 项目介绍

##### `SpringBoot` + `Netty-SocketIO`快速简单实现一个即时通讯聊天室(服务器给页面推送消息通知)

`Netty-SocketIO`是一个开源的、基于`Netty`的、`Java`版的即时消息推送项目。通过`Netty-SocketIO`，我们可以轻松的实现服务端主动向客户端推送消息的场景，比如说股票价格变化、K线图、消息提醒等。它和`WebSocket`有相同的作用，只不过`Netty-SocketIO`可支持所有的浏览器

#### 预览图示

```
聊天图示
```
![聊天图示](https://docs.dolyw.com/Project/NettyStudy/image/20190802001.gif)

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

* 配置文件

```yml
server:
    port: 8080

spring:
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
     *
     * @param
     * @return com.corundumstudio.socketio.SocketIOServer
     * @author wliduo[i@dolyw.com]
     * @date 2019/4/17 11:41
     */
    @Bean("socketIOServer")
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        // 配置端口
        config.setPort(port);
        // 开启Socket端口复用
        com.corundumstudio.socketio.SocketConfig socketConfig = new com.corundumstudio.socketio.SocketConfig();
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
     *
     * @param socketServer
     * @return com.corundumstudio.socketio.annotation.SpringAnnotationScanner
     * @author wliduo[i@dolyw.com]
     * @date 2019/7/31 18:21
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}
```

* 启动类

```java
/**
 * SpringBoot启动之后执行
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/4/17 11:45
 */
@Component
@Order(1)
public class SocketServer implements CommandLineRunner {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ServerRunner.class);

    /**
     * socketIOServer
     */
    private final SocketIOServer socketIOServer;

    @Autowired
    public SocketServer(SocketIOServer socketIOServer) {
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

##### 这样就配置完成了，`Netty-SocketIO`封装的挺简单的

#### 开始连接

先去下载socket.io.js:[https://www.bootcdn.cn/socket.io](https://www.bootcdn.cn/socket.io)，放进项目里，我是直接用的2.2.0，页面就直接用`SpringBoot`默认的`Thymeleaf`

* Socket消息对象

```java
/**
 * MessageDto
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/7/31 18:57
 */
public class MessageDto implements Serializable {

    /**
     * 源客户端用户名
     */
    private String sourceUserName;

    /**
     * 目标客户端用户名
     */
    private String targetUserName;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 空构造方法
     */
    public MessageDto() {
    }

    /**
     * 构造方法
     *
     * @param sourceUserName
     * @param targetUserName
     * @param msgType
     * @param msgContent
     */
    public MessageDto(String sourceUserName, String targetUserName, String msgType, String msgContent) {
        this.sourceUserName = sourceUserName;
        this.targetUserName = targetUserName;
        this.msgType = msgType;
        this.msgContent = msgContent;
    }

    public String getSourceUserName() {
        return sourceUserName;
    }

    public void setSourceUserName(String sourceUserName) {
        this.sourceUserName = sourceUserName;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
```

* SocketHandler事件类

```java
/**
 * SocketHandler
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/4/17 13:42
 */
@Component
public class SocketHandler {

    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(SocketHandler.class);

    /**
     * ConcurrentHashMap保存当前SocketServer用户ID对应关系
     */
    private Map<String, UUID> clientMap = new ConcurrentHashMap<>(16);

    public Map<String, UUID> getClientMap() {
        return clientMap;
    }

    public void setClientMap(Map<String, UUID> clientMap) {
        this.clientMap = clientMap;
    }

    /**
     * socketIOServer
     */
    private final SocketIOServer socketIOServer;

    @Autowired
    public SocketHandler(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    /**
     * 当客户端发起连接时调用
     *
     * @param socketIOClient
     * @return void
     * @author wliduo[i@dolyw.com]
     * @date 2019/4/17 13:55
     */
    @OnConnect
    public void onConnect(SocketIOClient socketIOClient) {
        String userName = socketIOClient.getHandshakeData().getSingleUrlParam("userName");
        if (StringUtils.isNotBlank(userName)) {
            logger.info("用户{}开启长连接通知, NettySocketSessionId: {}, NettySocketRemoteAddress: {}",
                    userName, socketIOClient.getSessionId().toString(), socketIOClient.getRemoteAddress().toString());
            // 保存
            clientMap.put(userName, socketIOClient.getSessionId());
            // 发送上线通知
            this.sendMsg(null, null,
                    new MessageDto(userName, null, MsgTypeEnum.ONLINE.getValue(), null));
        }
    }

    /**
     * 客户端断开连接时调用，刷新客户端信息
     *
     * @param socketIOClient
     * @return void
     * @author wliduo[i@dolyw.com]
     * @date 2019/4/17 13:56
     */
    @OnDisconnect
    public void onDisConnect(SocketIOClient socketIOClient) {
        String userName = socketIOClient.getHandshakeData().getSingleUrlParam("userName");
        if (StringUtils.isNotBlank(userName)) {
            logger.info("用户{}断开长连接通知, NettySocketSessionId: {}, NettySocketRemoteAddress: {}",
                    userName, socketIOClient.getSessionId().toString(), socketIOClient.getRemoteAddress().toString());
            // 移除
            clientMap.remove(userName);
            // 发送下线通知
            this.sendMsg(null, null,
                    new MessageDto(userName, null, MsgTypeEnum.OFFLINE.getValue(), null));
        }
    }

    /**
     * sendMsg发送消息事件
     *
     * @param socketIOClient
	 * @param ackRequest
	 * @param messageDto
     * @return void
     * @author wliduo[i@dolyw.com]
     * @date 2019/8/1 11:41
     */
    @OnEvent("sendMsg")
    public void sendMsg(SocketIOClient socketIOClient, AckRequest ackRequest, MessageDto messageDto) {
        if (messageDto != null) {
            // 全部发送
            clientMap.forEach((key, value) -> {
                if (value != null) {
                    socketIOServer.getClient(value).sendEvent("receiveMsg", messageDto);
                }
            });
        }
    }

}
```

* Socket实现网页common.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head th:fragment="headJq(title)">
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:text="${title}">加载中</title>
    <link rel="shortcut icon" th:href="@{/favicon.ico}" type="image/x-icon"/>
    <!-- 引入jquery，Moment，socket.io -->
    <script th:src="@{js/jquery.min.js}"></script>
    <script th:src="@{js/moment.min.js}"></script>
    <script th:src="@{js/socket.io.js}"></script>
</head>

</html>
```

* Socket实现网页index.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head th:include="/common/common :: headJq('聊天室')"></head>

<body>

    <div id="app">
        <input id="msgContent" name="msgContent" placeholder="请输入消息">
        <select id="msgType" name="msgType">
            <option value="00">全部</option>
        </select>
        <input type="button" onclick="sendMsg()" value="发送"/>
        <input type="button" onclick="cleanMsg()" value="清空"/>
        <ul id="msgList"></ul>
    </div>
    
    <script type="text/javascript" th:inline="javascript">
        var userName, socket;
        // Socket连接
        function initIm() {
            userName = prompt("请输入用户名进入聊天室");
            if ($.trim(userName)) {
                socket = io.connect("localhost:9090", {
                    'query': 'userName=' + userName
                });

                // 成功连接事件
                socket.on('connect', function () {});
                // 断开连接事件
                socket.on('disconnect', function () {});

                // 监听receiveMsg接收消息事件
                socket.on('receiveMsg', function (data) {
                    console.log(data);
                    var msgLi = "<li>" + moment().format('HH:mm:ss') + "&nbsp;&nbsp;&nbsp;";
                    if (data.msgType == '00') {
                        // 发送消息给全部人
                        msgLi = msgLi + data.sourceUserName + ": " + data.msgContent;
                    } else if (data.msgType == '01') {
                        // 上线通知
                        msgLi = msgLi +  "<span style='color: blue'>" + data.sourceUserName + "进入了聊天室</span>";
                    } else if (data.msgType == '02') {
                        // 下线通知
                        msgLi = msgLi +  "<span style='color: gray'>" + data.sourceUserName + "离开了聊天室</span>";
                    }
                    msgLi = msgLi +  "</li>";
                    $('#msgList').append(msgLi);
                });
            } else {
                alert("非法用户名");
            }
        }

        initIm();

        // 发送消息
        function sendMsg() {
            if ($.trim(userName)) {
                var msgContent = $.trim($("#msgContent").val());
                // 消息不能为空
                if (msgContent) {
                    socket.emit('sendMsg', {
                        "sourceUserName": userName,
                        "msgType": $("#msgType").val(),
                        "msgContent": msgContent
                    });
                } else {
                    alert("消息不能为空");
                }
                $("#msgContent").val('');
            } else {
                initIm();
            }
        }

        // 清空消息
        function cleanMsg() {
            var result = confirm("你确定清空消息吗");
            if (result) {
                $('#msgList').html('');
            }
        }
	</script>

</body>

</html>
```

* 最后还有一个启动配置类

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 设置首页
     *
     * @param registry
     * @return void
     * @author wliduo[i@dolyw.com]
     * @date 2019/1/24 19:18
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/view.shtml");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
```

##### 就这样一个简单的聊天室就实现了，实际请查看代码，最后用`Vue` + `ElementUI`美化了一下前端界面

#### 安装教程

```
运行项目src\main\java\com\example\Application.java即可，访问http://localhost:8080，开不同的浏览器窗口即可进行聊天
```

#### 搭建参考

1. 感谢tomoya92的在spring-boot项目里集成netty-socketio实现服务器给页面推送消息通知:[https://blog.yiiu.co/2018/08/20/spring-boot-netty-socketio](https://blog.yiiu.co/2018/08/20/spring-boot-netty-socketio)
2. 感谢脚本小娃子的端口复用技术简单了解；重用端口；socket复用端口:[https://www.cnblogs.com/shengulong/p/10206668.html](https://www.cnblogs.com/shengulong/p/10206668.html)
3. 感谢March On的Socket端口复用:[https://www.cnblogs.com/z-sm/p/10461456.html](https://www.cnblogs.com/z-sm/p/10461456.html)
4. 感谢elf8848的Java Socket 几个重要的TCP/IP选项解析(一):[https://elf8848.iteye.com/blog/1739598](https://elf8848.iteye.com/blog/1739598)
5. 感谢sun_t89的Spring Boot实战之netty-socketio实现简单聊天室(给指定用户推送消息):[https://blog.csdn.net/sun_t89/article/details/52060946](https://blog.csdn.net/sun_t89/article/details/52060946)
# 延迟任务场景技术选型

实现延迟任务的方式有很多，各有利弊，有单机和分布式的，延迟任务有别于定式任务，定式任务往往是固定周期的，有明确的触发时间，场景很多

* 支付超时取消订单
* 评价超时自动好评
* ...

下面来探讨一些实现方案

## 1. DelayQueue

<!-- * [DelayQueue实现](https://blog.52itstyle.vip/archives/5135/) -->

缺点是**单机运行在内存中导致 OOM、无法持久化、宕机任务丢失**

* Github: [https://github.com/dolyw/ProjectStudy/blob/master/JavaSource/src/test/java/containers/T04_BlockingQueue_3_DelayQueue.java](https://github.com/dolyw/ProjectStudy/blob/master/JavaSource/src/test/java/containers/T04_BlockingQueue_3_DelayQueue.java)
* Gitee(码云): [https://gitee.com/dolyw/ProjectStudy/blob/master/JavaSource/src/test/java/containers/T04_BlockingQueue_3_DelayQueue.java](https://gitee.com/dolyw/ProjectStudy/blob/master/JavaSource/src/test/java/containers/T04_BlockingQueue_3_DelayQueue.java)


### 1.1. Main

```java
import java.util.concurrent.*;

/**
 * DelayQueue - 按时间排序出队列
 *
 * 任务调度 - 定时任务 - 延时队列
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T04_BlockingQueue_3_DelayQueue {

    public static BlockingQueue<MyTask> tasks = new DelayQueue<>();

    public static class MyTask implements Delayed {
        public String name;
        public long runningTime;

        MyTask(String name, long rt) {
            this.name = name;
            this.runningTime = rt;
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public String toString() {
            return name + " " + runningTime;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        MyTask t1 = new MyTask("t1", now + 10000);
        MyTask t2 = new MyTask("t2", now + 20000);
        MyTask t3 = new MyTask("t3", now + 15000);
        MyTask t4 = new MyTask("t4", now + 25000);
        MyTask t5 = new MyTask("t5", now + 5000);

        tasks.put(t1);
        tasks.put(t2);
        tasks.put(t3);
        tasks.put(t4);
        tasks.put(t5);

        System.out.println(tasks);

        /*for (int i = 0; i < 5; i++) {
            System.out.println(tasks.take());
        }

        tasks.put(t1);
        tasks.put(t2);
        tasks.put(t3);
        tasks.put(t4);
        tasks.put(t5);*/

        MyTask t = null;
        while (tasks.size() > 0) {
            t = tasks.take();
            System.out.println(t.runningTime + ", " + System.currentTimeMillis());
        }
    }
}
```

## 2. HashedWheelTimer

Netty 提供的 HashedWheelTimer 工具类来实现延迟任务，采用时间轮算法，相比 DelayQueue 的数据结构，时间轮在算法复杂度上有一定优势。DelayQueue 由于涉及到排序，需要调堆，插入和移除的复杂度是 O(lgn)，而时间轮在插入和移除的复杂度都是 O(1)

<!-- * [Netty提供的HashedWheelTimer工具类来实现延迟任务](https://blog.52itstyle.vip/archives/5150/) -->

缺点是**单机运行在内存中导致 OOM、无法持久化、宕机任务丢失**

* Github: [https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/DelayTask](https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/DelayTask)
* Gitee(码云): [https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/DelayTask](https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/DelayTask)

### 2.1. pom

```xml
<!-- HashedWheelTimer -->
<dependency>
    <groupId>io.netty</groupId>
    <artifactId>netty-common</artifactId>
    <version>4.1.23.Final</version>
</dependency>
```

### 2.2. Task

```java
/**
 * HashedWheelTimer实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 11:50
 */
public class CustomTimerTask implements TimerTask {

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 任务ID
     */
    private final long taskId;

    /**
     * 创建时间戳
     */
    private final long timestamp;

    public CustomTimerTask(long taskId) {
        this.taskId = taskId;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public void run(Timeout timeout) {
        // 异步处理任务
        System.out.println(
                String.format("任务执行时间:%s，任务创建时间:%s，任务ID:%s",
                        LocalDateTime.now().format(F),
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).format(F),
                        taskId
                )
        );
    }
}
```

### 2.3. Main

```java
/**
 * RunHashedWheelTimer运行
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 11:54
 */
public class RunHashedWheelTimer {

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) throws Exception {

        ThreadFactory factory = r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("HashedWheelTimerWorker");
            return thread;
        };

        /**
         * @param tickDuration 每tick一次的时间间隔
         * @param unit tickDuration的时间单位
         * @param ticksPerWheel 时间轮中的槽数
         * @param leakDetection 检查内存溢出
         */
        Timer timer = new HashedWheelTimer(factory, 1,
                TimeUnit.SECONDS, 100, true);
        System.out.println(String.format("开始任务时间:%s", LocalDateTime.now().format(F)));

        // 任务ID-6，5秒后执行
        TimerTask timerTask = new CustomTimerTask(6);
        timer.newTimeout(timerTask, 5, TimeUnit.SECONDS);

        Thread.sleep(Integer.MAX_VALUE);
    }

}
```

## 3. 任务调度

任务调度按设定时间间隔执行一次，及时性可能没有那么块，必须等到任务调用执行，选取那种任务调度框架(Quartz，XXL-JOB，OhMyScheduler等)了

**缺点是需要全表扫描(当然可以每次处理指定时间段的数据)**，任务设置轮询时间就是最大延迟时间，对数据库有一定压力，**仅适合数据量少的业务场景**

## 4. Redis ZSet

Redis 中的 ZSet 是一个有序的 Set，内部使用 HashMap 和跳表(SkipList)来保证数据的存储和有序，HashMap 里放的是成员到 score 的映射，而跳跃表里存放的是所有的成员，排序依据是 HashMap 里存的 score，使用跳跃表的结构可以获得比较高的查找效率，并且在实现上比较简单，借助 ZSet 数据类型，把延迟任务存储在此数据集合中，然后在开启一个无线循环查询当前时间的所有任务进行消费

但是可能有并发问题，即两个线程或者两个进程都会拿到一样的数据，然后重复执行，最后又都会删除。如果是单机多线程执行，或者分布式环境下，可以使用 Redis 事务，也可以使用由 Redis 实现的分布式锁，或者使用下例中 Redis Script。你可以在 Redis 官方的 Transaction 章节找到事务的相关内容

**这种方式比较推荐，可以满足持久化，分布式的场景**，使用的话，Redisson 框架有封装好，直接使用即可，如下简单实现一个订单超时自动评价功能

<!-- * [推荐一款基于Redis的高可用延迟队列](https://blog.52itstyle.vip/archives/5163/)
* [Redisson延迟队列RDelayedQueue的使用](https://www.jianshu.com/p/f472af134ce0) -->

* Github: [https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/DelayTask](https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/DelayTask)
* Gitee(码云): [https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/DelayTask](https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/DelayTask)

### 4.1. pom

```xml
<!-- JDK 1.6+ compatible -->
<!--<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson</artifactId>
    <version>2.10.4</version>
</dependency>-->

<!-- JDK 1.8+ compatible，Redisson包含了netty -->
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson</artifactId>
    <version>3.12.1</version>
</dependency>
```

### 4.2. Config

```yml
spring:
  redis:
    database: 2
    host: 127.0.0.1
    port: 6379
    password: 
```

```java
/**
 * RedissonConfig配置
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 16:16
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    private String host;

    private int port;

    private String password;

    private int database;

    /**
     * RedissonClient配置
     *
     * @param
     * @return org.redisson.api.RedissonClient
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/8/14 16:25
     */
    @Bean
    public RedissonClient redissonClient() {
        RedissonClient redissonClient = null;
        Config config = new Config();
        String url = "redis://" + host + ":" + port;
        // 单节点配置
        config.useSingleServer().setAddress(url).setDatabase(database);
        // 主从配置
        /*config.useMasterSlaveServers()
                // 设置redis主节点
                .setMasterAddress("redis://192.168.1.120:6379")
                // 设置redis从节点
                .addSlaveAddress("redis://192.168.1.130:6379", "redis://192.168.1.140:6379");*/
        // 哨兵部署方式，sentinel是采用Paxos拜占庭协议，一般sentinel至少3个节点
        /*config.useSentinelServers()
                .setMasterName("my-sentinel-name")
                .addSentinelAddress("redis://192.168.1.120:6379")
                .addSentinelAddress("redis://192.168.1.130:6379")
                .addSentinelAddress("redis://192.168.1.140:6379");*/
        // 集群部署方式，cluster方式至少6个节点，3主3从，3主做sharding，3从用来保证主宕机后可以高可用
        /*config.useClusterServers()
                // 集群状态扫描间隔时间，单位是毫秒
                .setScanInterval(2000)
                .addNodeAddress("redis://192.168.1.120:6379")
                .addNodeAddress("redis://192.168.1.130:6379")
                .addNodeAddress("redis://192.168.1.140:6379")
                .addNodeAddress("redis://192.168.1.150:6379")
                .addNodeAddress("redis://192.168.1.160:6379")
                .addNodeAddress("redis://192.168.1.170:6379");*/
        // 云托管部署方式，这种方式主要解决redis提供商为云服务的提供商的redis连接，比如亚马逊云、微软云
        /*config.useReplicatedServers()
                // 主节点变化扫描间隔时间
                .setScanInterval(2000)
                .addNodeAddress("redis://192.168.1.120:6379")
                .addNodeAddress("redis://192.168.1.130:6379")
                .addNodeAddress("redis://192.168.1.140:6379");*/
        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            logger.error("RedissonClient init redis url:[{}], Exception:", url, e);
            return null;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
}
```

### 4.3. Util

```java
/**
 * RedissonDelayedUtil
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 16:34
 */
@Component
public class RedissonDelayedUtil<T> {

    private static final Logger logger = LoggerFactory.getLogger(RedissonDelayedUtil.class);

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 添加延时队列任务
     *
     * @param blockingQueueName 队列名
	 * @param t 对象
	 * @param delay 时间
	 * @param timeUnit 时间单位
     * @return boolean
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/8/14 16:41
     */
    public boolean offer(String blockingQueueName, T t, long delay, TimeUnit timeUnit) {
        RBlockingQueue<T> blockingQueue = null;
        RDelayedQueue<T> delayedQueue = null;
        try {
            blockingQueue = redissonClient.getBlockingQueue(blockingQueueName);
            delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
            delayedQueue.offer(t, delay, timeUnit);
            // https://blog.csdn.net/zsj777/article/details/105223853
            // 解决延迟队列take数据阻塞不执行，必须等到下一个内容offer时，队列才会把阻塞的消息全部处理掉
            // offer后再offer一个空值即可
            delayedQueue.offer(null, 1, TimeUnit.MILLISECONDS);
            return true;
        } catch (Exception e) {
            logger.error("RedissonDelayedUtil put Exception:", e);
        } finally {
            if (delayedQueue != null) {
                delayedQueue.destroy();
            }
        }
        return false;
    }

    /**
     * 获取延时队列任务
     *
     * @param blockingQueueName 队列名
     * @return org.redisson.api.RBlockingQueue<T>
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/8/14 17:03
     */
    public RBlockingQueue<T> take(String blockingQueueName) {
        RBlockingQueue<T> blockingQueue = null;
        RDelayedQueue<T> delayedQueue = null;
        try {
            blockingQueue = redissonClient.getBlockingQueue(blockingQueueName);
            delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
            // 解决延迟队列首次启动获取为空的问题，先offer一个空值就没问题了
            delayedQueue.offer(null, 1, TimeUnit.MILLISECONDS);
            return blockingQueue;
        } catch (Exception e) {
            logger.error("RedissonDelayedUtil put Exception:", e);
        } finally {
            if (delayedQueue != null) {
                delayedQueue.destroy();
            }
        }
        return null;
    }

}
```

### 4.4. Model

```java
/**
 * OrderDto
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 16:31
 */
public class OrderDto implements Serializable {

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 订单名
     */
    private String orderName;

    public OrderDto(String orderCode) {
        this.orderCode = orderCode;
    }

    public OrderDto(String orderCode, String orderName) {
        this.orderCode = orderCode;
        this.orderName = orderName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}
```

```java
/**
 * RedissonDelayedQueue枚举
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 16:43
 */
public enum RedissonDelayedEnum {

    /**
     * 订单超时默认评价
     */
    ORDER_DEFAULT_EVALUATION("orderDefaultEvaluation", 1, TimeUnit.MINUTES);

    public String name;

    public int delay;

    public TimeUnit timeUnit;

    RedissonDelayedEnum(String name, int delay, TimeUnit timeUnit) {
        this.name = name;
        this.delay = delay;
        this.timeUnit = timeUnit;
    }
}
```

### 4.5. Impl

```java
/**
 * Redisson延时队列测试
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 16:23
 */
@RestController
@RequestMapping("/")
public class WebController {

    @Autowired
    private RedissonDelayedUtil redissonDelayedUtil;

    @GetMapping
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/put")
    public void put() {
        // 10秒后执行
        redissonDelayedUtil.offer(RedissonDelayedEnum.ORDER_DEFAULT_EVALUATION.name,
                new OrderDto(String.valueOf(System.currentTimeMillis()), "Test"),
                RedissonDelayedEnum.ORDER_DEFAULT_EVALUATION.delay,
                RedissonDelayedEnum.ORDER_DEFAULT_EVALUATION.timeUnit);
    }

    @GetMapping("/putName")
    public void putName(@RequestParam("name") String name) {
        // 10秒后执行
        redissonDelayedUtil.offer(RedissonDelayedEnum.ORDER_DEFAULT_EVALUATION.name,
                new OrderDto(String.valueOf(System.currentTimeMillis()), name),
                RedissonDelayedEnum.ORDER_DEFAULT_EVALUATION.delay,
                RedissonDelayedEnum.ORDER_DEFAULT_EVALUATION.timeUnit);
    }

}
```

```java
/**
 * 延时任务启动执行，自动消费
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 17:01
 */
@Component
@Order
public class DelayedTask implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DelayedTask.class);

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Autowired
    private RedissonDelayedUtil redissonDelayedUtil;

    @Override
    public void run(String... args) throws Exception {
        // 订单默认评价
        this.orderDefaultEvaluation();
    }

    /**
     * 订单默认评价
     *
     * @param
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/8/14 17:10
     */
    public void orderDefaultEvaluation() throws InterruptedException {
        RBlockingQueue<OrderDto> blockingQueue = redissonDelayedUtil.take(RedissonDelayedEnum.ORDER_DEFAULT_EVALUATION.name);
        if (blockingQueue == null) {
            logger.error("延时任务启动失败");
            return;
        }
        while (true) {
            OrderDto orderDto = blockingQueue.take();
            if (orderDto == null) {
                continue;
            }
            logger.info("名称: {}，执行时间: {}，入队时间: {}", orderDto.getOrderName(), LocalDateTime.now().format(F),
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(
                            Long.parseLong(orderDto.getOrderCode())), ZoneId.systemDefault()
                    ).format(F));
        }
    }

}
```

## 5. Redis键通知

* [Redis键通知机制](https://www.cnblogs.com/hld123/p/10812848.html)
* [Springboot+Redis实现过期键通知](https://blog.csdn.net/qq_42651904/article/details/106279593)

Redis 键通知是不可靠的

* 开启键通知会对 Redis 有额外的开销
* 键通知暂时 Redis 并不保证消息必达，Redis 客户端断开连接所有 Key 丢失
* 消费速度不可自控，如果一瞬间 QPS 非常高，接收到的通知会非常密集，消费不过来

## 6. MQ延迟队列

几乎所有的 MQ 中间件都可以实现延迟任务，在这里更准确的叫法应该叫延迟队列，MQ 可以做到消息零丢失，可抗高并发，需要额外引入 MQ 中间件，提高系统复杂性和 MQ 高可用维护性

如果专门开启一个 MQ 中间件来执行延迟任务，就有点杀鸡用宰牛刀般的奢侈了，不过已经有了 MQ 环境的话，用它来实现延迟任务的话，还是可取的

### 6.1. RabbitMQ

* RabbitMQ 的 TTL 和 DXL 实现延迟队列，通过消息过期后进入死信交换器，再由交换器转发到延迟消费队列
* 使用 rabbitmq-delayed-message-exchange 插件实现延迟功能

延迟插件 rabbitmq-delayed-message-exchange 是在 RabbitMQ 3.5.7 及以上的版本才支持的，依赖 Erlang/OPT 18.0 及以上运行环境

* [RabbitMQ的使用 - 死信队列](https://note.dolyw.com/mq/11-RabbitMQ-Use.html#_3-%E6%AD%BB%E4%BF%A1%E9%98%9F%E5%88%97)

由于使用死信交换器比较麻烦，所以推荐使用第二种实现方式 rabbitmq-delayed-message-exchange 插件的方式实现延迟队列

**参考**

* [延迟任务的实现总结](https://blog.csdn.net/xybelieve1990/article/details/78040419)
* [史上最全的延迟任务实现方式汇总！附代码（强烈推荐）](https://www.cnblogs.com/vipstone/p/12696465.html)
* [Springboot+Redis实现过期键通知（订单超时取消方案总结）](https://blog.csdn.net/qq_42651904/article/details/106279593)
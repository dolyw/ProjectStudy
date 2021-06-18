# 浅析分布式ID

传统的单体架构的时候，我们基本是单库然后业务单表的结构。每个业务表的 ID 一般我们都是从 1 增

但是在分布式服务架构模式下分库分表的设计，使得多个库或多个表存储相同的业务数据。这种情况根据数据库的自增 ID 就会产生相同 ID 的情况，不能保证主键的唯一性

在复杂分布式系统中，往往需要对大量的数据和消息进行唯一标识，由此产生了分布式 ID

## 1. 特性要求

那业务系统对 ID 号的要求有哪些呢？

* **全局唯一**：不能出现重复的 ID 号，既然是唯一标识，这是最基本的要求
* **趋势递增**：在 MySQL InnoDB 引擎中使用的是聚集索引，由于多数 RDBMS 使用 B-tree 的数据结构来存储索引数据，在主键的选择上面我们应该尽量使用有序的主键保证写入性能
* **单调递增**：保证下一个 ID 一定大于上一个 ID，例如事务版本号、IM 增量消息、排序等特殊需求
* **信息安全**：如果 ID 是连续的，恶意用户的扒取工作就非常容易做了，直接按照顺序下载指定 URL 即可；如果是订单号就更危险了，竞对可以直接知道我们一天的单量。所以在一些应用场景下，会需要 ID 无规则、不规则

上述 123 对应三类不同的场景，3 和 4 需求还是互斥的，无法使用同一个方案满足

同时除了对 ID 号码自身的要求，业务还对 ID 号生成系统的可用性要求极高，想象一下，如果 ID 生成系统瘫痪，整个架构系统都无法执行，这就会带来一场灾难。由此总结一个 ID 生成服务应该做到如下几点

* **平均延迟和 TP999 延迟都要尽可能低**
* **可用性 5 个 9**
* **高 QPS**

## 2. UUID

UUID 的标准型式包含 32 个 16 进制数字，以连字号分为五段，形式为 8-4-4-4-12 的 36 个字符，到目前为止业界一共有 5 种方式生成 UUID，详见：[UUID](https://www.cnblogs.com/jajian/p/11101213.html)

### 2.1. 优点

* 性能非常高
* 生成方便，本地生成没有网络消耗
* 具备全球唯一的特性，对于数据库迁移不存在单号重复

### 2.2. 缺点

* 不易于存储，UUID 太长，很多场景不适用
* 基于 MAC 地址生成 UUID 的算法可能会造成 MAC 地址泄露
  - 这个漏洞曾被用于寻找梅丽莎病毒的制作者位置
* 无一定业务含义，可读性差
* ID 作为主键时在特定的环境会存在一些问题，在做 DB 主键的场景下，UUID 就非常不适用
  - MySQL 官方有明确的建议主键要尽量越短越好，36个字符长度的UUID不符合要求
  - MySQL 索引不利，在 InnoDB 引擎下，UUID 的无序性可能会引起数据位置频繁变动，严重影响性能

### 2.3. 适合场景

* 可以用来生成如 Token 令牌一类的场景，足够没辨识度，而且无序可读，长度足够
* 可以用于无纯数字要求、无序自增、无可读性要求的场景

## 3. 数据库

> 单体数据库自增就不说了，这里说明的是多实例分布式数据库自增

由于分布式数据库的起始自增值一样所以才会有冲突的情况发生，那么我们将分布式系统中数据库的同一个业务表的自增 ID 设计成不一样的起始值，然后设置固定的步长，步长的值即为分库的数量或分表的数量

以 MySQL 举例，利用给字段设置 auto_increment_increment 和 auto_increment_offset 来保证 ID 自增

* auto_increment_offset：表示自增长字段从那个数开始，其默认值是 1
* auto_increment_increment：表示自增长字段每次递增的量，其默认值是 1

假设有三台机器，则 DB1 中 order 表的起始 ID 值为 1，DB2 中 order 表的起始值为 2，DB3 中 order 表的起始值为 3，它们自增的步长都为 3，则它们的 ID 生成范围如下所示

* DB1: 1、4、7、10.....
* DB2: 2、5、8、11.....
* DB1: 3、6、9、12.....

### 3.1. 优点

* 解决了 ID 生成的单点问题
* ID 没有了单调递增的特性，只能趋势递增，这个缺点对于一般业务需求不是很重要，可以容忍

### 3.2. 缺点

* 系统水平扩展比较困难，必须定好步长
* 强依赖数据库，数据库压力还是很大，每次获取 ID 都得读写一次数据库，只能靠堆机器来提高性能

::: tip 定义好了步长和机器台数之后，如果要添加机器该怎么做？
假设现在只有一台机器发号是 1，2，3（步长是1），这个时候需要扩容机器一台。可以这样做：把第二台机器的初始值设置得比第一台超过很多，比如 14（假设在扩容时间之内第一台不可能发到 14），同时设置步长为 2，那么这台机器下发的号码都是 14 以后的偶数。然后摘掉第一台，把 ID 值保留为奇数，比如 7，然后修改第一台的步长为 2。让它符合我们定义的号段标准，对于这个例子来说就是让第一台以后只能产生奇数。扩容方案看起来复杂吗？貌似还好，现在想象一下如果我们线上有 100 台机器，这个时候要扩容该怎么做？简直是噩梦。所以系统水平扩展方案复杂难以实现
:::

### 3.3. 适合场景

* 数据量不大，数据库不需要扩容的场景

这种方案，除了难以适应大规模分布式和高并发的场景，普通的业务规模还是能够胜任的，所以这种方案还是值得积累

## 4. Redis

Redis 实现分布式 ID 主要是通过提供像 INCR 和 INCRBY 这样的自增原子命令，由于 Redis 自身的单线程的特点所以能保证生成的 ID 肯定是唯一有序的，但是集群的方式又会涉及到和数据库集群同样的问题，所以也需要设置分段和步长来实现

为了避免长期自增后数字过大可以通过与当前时间戳组合起来使用，另外为了保证并发和业务多线程的问题可以采用 Redis + Lua 的方式进行编码，保证安全

### 4.1. 优点

* 性能比较高

### 4.2. 缺点

* 强依赖 Redis
* 系统水平扩展比较困难，必须定好步长，和数据库类似

### 4.3. 适用场景

* 数据量不大，不需要扩容的场景

和数据库类似，这种方案，除了难以适应大规模分布式和高并发的场景，普通的业务规模还是能够胜任的

## 5. Snowflake

经典算法，后续百度 UidGenerator，美团 Leaf 都是以 Twitter 的 SnowFlake 改进的

* [理解分布式ID生成算法SnowFlake](https://segmentfault.com/a/1190000011282426)

Snowflake 的 Twitter 官方原版是用 Scala 写的，下面贴一个转换为 Java 的代码

```java
package com.example.util;

/**
 * Twitter的SnowFlake算法<br>
 *
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/1/15 11:31
 */
public class IdWorker {

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence;

    /**
     * 构造函数
     *
     * @param workerId
     * @param datacenterId
     * @param sequence
     */
    public IdWorker(long workerId, long datacenterId, long sequence) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        System.out.printf("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    /**
     * 开始时间截
     */
    private long twepoch = 1288834974657L;

    /**
     * 机器id所占的位数
     */
    private long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private long datacenterIdBits = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 序列在id中占的位数
     */
    private long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    private long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        IdWorker worker = new IdWorker(1, 1, 1);
        for (int i = 0; i < 30; i++) {
            System.out.println(worker.nextId());
        }
    }

}
```

### 5.1. 优点

* 毫秒数在高位，自增序列在低位，整个 ID 都是趋势递增的
* 不依赖数据库等第三方系统，以服务的方式部署，稳定性更高，生成 ID 的性能也是非常高的
* 可以根据自身业务特性分配 bit 位，非常灵活

### 5.2. 缺点

* 强依赖机器时钟，如果机器上时钟回拨，会导致发号重复或者服务会处于不可用状态，官方对于此并没有给出解决方案，而是简单的抛错处理

## 6. UidGenerator

百度的 [UidGenerator](https://github.com/baidu/uid-generator) 是百度开源基于 Java 语言实现的唯一 ID 生成器，是在雪花算法 Snowflake 的基础上做了一些改进

提供了两种生成方式 DefaultUidGenerator 和 CachedUidGenerator

## 7. Leaf

美团的 [Leaf](https://github.com/Meituan-Dianping/Leaf) 也是在雪花算法 Snowflake 的基础上做了一些改进

提供了两种生产方式 Leaf-segment 和 Leaf-snowflake

* [Leaf：美团分布式ID生成服务开源](https://tech.meituan.com/2019/03/07/open-source-project-leaf.html)

**参考**

* [分布式系统ID的几种生成办法](https://www.cnblogs.com/huchong/p/11400888.html)
* [分布式全局ID生成方案](https://www.cnblogs.com/jajian/p/11101213.html)
* [Leaf——美团点评分布式ID生成系统](https://tech.meituan.com/2017/04/21/mt-leaf.html)
* [分布式ID生成服务，真的有必要搞一个](https://mp.weixin.qq.com/s/WM_C2cPOuq4jbYCmuP0OIw)
* [Twitter的雪花算法（snowflake）自增ID](https://blog.csdn.net/zzzgd_666/article/details/81509216)


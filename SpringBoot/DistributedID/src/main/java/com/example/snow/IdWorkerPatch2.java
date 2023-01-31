package com.example.snow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * 改良版-Twitter的SnowFlake算法<br>
 *
 * 由于跨毫秒后，最后的Sequence累加就会清零，末位为偶数
 * 如果ID生成不频繁，则生成的就是全是偶数
 * 改良版雪花算法，解决全为偶数问题，保证低并发时奇偶交替
 *
 * 时钟回拨问题直接抛出异常，过于简单
 * 优化如果时间偏差大小小于5ms，则等待两倍时间重试一次，加强可用性
 *
 * 根据网络MAC和IP地址及JvmID取余自动设定workerId及datacenterId
 * https://gitee.com/yu120/sequence
 *
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）
 * 41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高
 * 经测试，SnowFlake每秒能够产生26万ID左右
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/1/15 11:31
 */
public class IdWorkerPatch2 {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(IdWorkerPatch2.class);

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

    private static volatile InetAddress LOCAL_ADDRESS = null;
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    /**
     * 构造函数
     */
    public IdWorkerPatch2() {
        this.datacenterId = getDatacenterId();
        this.workerId = getMaxWorkerId(datacenterId);
    }

    /**
     * 基于网卡MAC地址计算余数作为数据中心
     * <p>
     * 可自定扩展
     */
    protected long getDatacenterId() {
        long id = 0L;
        try {
            NetworkInterface network = NetworkInterface.getByInetAddress(getLocalAddress());
            if (null == network) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long) mac[mac.length - 2]) | (0x0000FF00 & (((long) mac[mac.length - 1]) << 8))) >> 6;
                    id = id % (maxDatacenterId + 1);
                }
            }
        } catch (Exception e) {
            logger.warn(" getDatacenterId: " + e.getMessage());
        }

        return id;
    }

    /**
     * 基于 MAC + PID 的 hashcode 获取16个低位
     * <p>
     * 可自定扩展
     */
    protected long getMaxWorkerId(long datacenterId) {
        StringBuilder mpId = new StringBuilder();
        mpId.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (name != null && name.length() > 0) {
            // GET jvmPid
            mpId.append(name.split("@")[0]);
        }

        // MAC + PID 的 hashcode 获取16个低位
        return (mpId.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 开始时间截
     */
    private long twepoch = 1288834974657L;

    /**
     * 机器ID所占的位数
     */
    private long workerIdBits = 5L;

    /**
     * 数据标识ID所占的位数
     */
    private long datacenterIdBits = 5L;

    /**
     * 支持的最大机器ID，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识ID，结果是31
     */
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 序列在ID中占的位数
     */
    private long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    private long workerIdShift = sequenceBits;

    /**
     * 数据标识ID向左移17位(12+5)
     */
    private long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 生成序列的掩码，这里为4095(0b111111111111=0xfff=4095)
     */
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 上一次的序列号，解决并发量小总是偶数的问题
     */
    private long lastSequence = 0L;

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    // 时间偏差大小小于5ms，则等待两倍时间重试一次
                    wait(offset << 1);
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        // 还是小于，抛异常
                        logger.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
                        throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                                lastTimestamp - timestamp));
                    }
                } catch (InterruptedException e) {
                    logger.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
                    throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));
                }
            } else {
                logger.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                        lastTimestamp - timestamp));
            }
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1L) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0L) {
                // 阻塞到下一个毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
            // 根据上一次Sequence决定本次序列从0还是1开始，保证低并发时奇偶交替
            if (lastSequence == 0L) {
                sequence = 1L;
            }
        }

        // 上次的序列号
        lastSequence = sequence;
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
     * Find first valid IP from local network card
     *
     * @return first valid local IP
     */
    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }

        LOCAL_ADDRESS = getLocalAddress0();
        return LOCAL_ADDRESS;
    }

    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
            logger.warn("Failed to retrieving ip address, " + e.getMessage(), e);
        }

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            try {
                                InetAddress address = addresses.nextElement();
                                if (isValidAddress(address)) {
                                    return address;
                                }
                            } catch (Throwable e) {
                                logger.warn("Failed to retrieving ip address, " + e.getMessage(), e);
                            }
                        }
                    } catch (Throwable e) {
                        logger.warn("Failed to retrieving ip address, " + e.getMessage(), e);
                    }
                }
            }
        } catch (Throwable e) {
            logger.warn("Failed to retrieving ip address, " + e.getMessage(), e);
        }

        logger.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }

        String name = address.getHostAddress();
        return (name != null && !"0.0.0.0".equals(name) && !"127.0.0.1".equals(name) && IP_PATTERN.matcher(name).matches());
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
     * 由于跨毫秒后，最后的Sequence累加就会清零，末位为偶数
     * 如果ID生成不频繁，则生成的就是全是偶数
     * 改良版雪花算法，解决全为偶数问题，保证低并发时奇偶交替
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        IdWorkerPatch2 idWorkerPatch = new IdWorkerPatch2();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000L);
            logger.info("{}", idWorkerPatch.nextId());
        }
    }

}

package com.example.redisson;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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

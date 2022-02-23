package com.example.redisson;

import org.redisson.api.RBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 延时任务启动执行，自动消费
 * https://www.jianshu.com/p/f80f833ab8f6
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

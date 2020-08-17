package com.example.redisson;

import java.util.concurrent.TimeUnit;

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

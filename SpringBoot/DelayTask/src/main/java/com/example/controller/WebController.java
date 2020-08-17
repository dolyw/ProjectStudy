package com.example.controller;

import com.example.redisson.OrderDto;
import com.example.redisson.RedissonDelayedEnum;
import com.example.redisson.RedissonDelayedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

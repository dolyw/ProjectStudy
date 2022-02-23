package com.example.delaytask;

import com.example.Application;
import com.example.redisson.RedisLockHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DelaytaskApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private RedisLockHelper redisLockHelper;

    @Test
    void redisLock() throws Exception {
        System.out.println("xxxx");
        try {
            redisLockHelper.tryLock("order:pay:" + 1, () -> {
                FileInputStream fileInputStream = null;
                fileInputStream.read();
                // 业务逻辑
            });
        } catch (Exception e) {
            System.out.println(e);
        }

        Boolean payResult = redisLockHelper.tryLock("order:pay:" + 2, () -> {
            // 业务逻辑
            return true;
        });

        Integer payResult2 = redisLockHelper.tryLock("order:pay:" + 2, () -> {
            // 业务逻辑
            return 0;
        });

        String payResult3 = redisLockHelper.tryLock("order:pay:" + 2, () -> {
            // 业务逻辑
            return "";
        });
    }

}

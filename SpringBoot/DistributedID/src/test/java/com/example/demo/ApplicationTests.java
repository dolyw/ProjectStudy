package com.example.demo;

import com.example.snow.generator.IdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ApplicationTests
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/9/3 17:39
 */
@SpringBootTest
class ApplicationTests {

    @Autowired
    private IdGenerator idGenerator;

    @Test
    void contextLoads() {
        System.out.println("HelloWorld");
    }

    /**
     * 测试SnowUserId
     *
     * @throws Exception
     */
    @Test
    void testSnowUserId() throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000L);
            System.out.println(idGenerator.nextUserId());
        }
    }

    /**
     * 测试SnowOrderCode
     *
     * @throws Exception
     */
    @Test
    void testSnowOrderCode() throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000L);
            System.out.println(idGenerator.nextOrderCode());
        }
    }

}

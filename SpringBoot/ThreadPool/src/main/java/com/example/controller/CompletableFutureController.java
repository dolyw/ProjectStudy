package com.example.controller;

import com.example.helper.BusinessHelper;
import com.example.util.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * CompletableFutureController
 *
 * https://www.cnblogs.com/happyliu/archive/2018/08/12/9462703.html
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/19 14:46
 */
@RestController
@RequestMapping("/cf")
public class CompletableFutureController {

    /**
     * logger
     */
    private final static Logger logger = LoggerFactory.getLogger(CompletableFutureController.class);

    @Autowired
    private BusinessHelper businessHelper;

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private ThreadPoolExecutor jdkThreadPoolExecutor;

    @Autowired
    private ThreadPoolExecutor ioThreadPoolExecutor;

    /**
     * 无返回值
     *
     * @param
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/20 9:53
     */
    @GetMapping("/run1")
    public String run1() throws Exception {
        logger.info("run1开始执行");
        CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
            try {
                businessHelper.handle1();
            } catch (Exception e) {

            }
            throw new RuntimeException();
        });
        CompletableFuture completableFuture2 = CompletableFuture.runAsync(() -> {
            try {
                businessHelper.handle2();
            } catch (Exception e) {

            }
        }, jdkThreadPoolExecutor);
        // Thread.sleep(5000);
        // 一个执行完就结束
        CompletableFuture.anyOf(completableFuture, completableFuture2).get();
        // 全部执行完才结束
        CompletableFuture.allOf(completableFuture, completableFuture2).get();
        logger.info("run1执行完成");
        return "run1 success";
    }

    /**
     * 无返回值
     *
     * @param
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/20 9:53
     */
    @GetMapping("/run2")
    public String run2() throws Exception {
        logger.info("run2开始执行");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return businessHelper.handle1();
            } catch (Exception e) {
                return "";
            }
        }, ioThreadPoolExecutor);
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                // return businessHelper.handle2();
                return this.handle3();
            } catch (Exception e) {
                return "";
            }
        }, jdkThreadPoolExecutor);
        // Thread.sleep(5000);
        // 一个执行完就结束
        // CompletableFuture.anyOf(completableFuture, completableFuture2).get();
        // 全部执行完才结束
        CompletableFuture.allOf(completableFuture, completableFuture2).get();

        logger.info(Boolean.toString(completableFuture.isDone()));
        logger.info(Boolean.toString(completableFuture2.isDone()));
        logger.info("run2执行完成");
        return "run2 success";
    }

    public String handle3() throws Exception {
        Thread.sleep(3000L);
        logger.info("handle3");
        return "handle3";
    }

    /**
     * 工具类异步
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/20 10:59
     */
    @GetMapping("/sms")
    public String sms() throws Exception {
        logger.info("sms开始执行");
        smsUtil.send("15912347896", "135333");
        logger.info("sms执行完成");
        return "send sms success";
    }
}

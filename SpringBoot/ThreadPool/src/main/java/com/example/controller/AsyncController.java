package com.example.controller;

import com.example.service.AsyncService;
import com.example.util.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * AsyncController
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/19 14:46
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    /**
     * logger
     */
    private final static Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private SmsUtil smsUtil;

    /**
     * 可以看到无返回值异步方法出现异常，主线程还是继续执行完成
     *
     * @param
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/20 9:53
     */
    @GetMapping("/run1")
    public String run1() throws Exception {
        asyncService.task1();
        logger.info("run1开始执行");
        Thread.sleep(5000);
        logger.info("run1执行完成");
        return "run1 success";
    }

    /**
     * 可以看到有返回值异步方法出现异常，异常抛给主线程处理，导致主线程也被中断执行
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/20 10:15
     */
    @GetMapping("/run2")
    public String run2() throws Exception {
        Future<String> future = asyncService.task2();
        // get()方法阻塞主线程，直到执行完成
        String result = future.get();
        logger.info("run2开始执行");
        Thread.sleep(5000);
        logger.info("run2执行完成");
        return result;
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
        smsUtil.sendCode("15912347896", "135333");
        logger.info("sms执行完成");
        return "send sms success";
    }
}

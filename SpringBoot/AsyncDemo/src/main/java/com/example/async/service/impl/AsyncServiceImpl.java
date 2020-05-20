package com.example.async.service.impl;

import com.example.async.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * AsyncServiceImpl
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/19 14:24
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    /**
     * logger
     */
    private final static Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Override
    @Async("threadPoolTaskExecutor")
    public void task1() throws Exception {
        logger.info("task1开始执行");
        Thread.sleep(3000);
        logger.info("task1执行结束");
        throw new RuntimeException("出现异常");
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public Future<String> task2() throws Exception {
        logger.info("task2开始执行");
        Thread.sleep(3000);
        logger.info("task2执行结束");
        throw new RuntimeException("出现异常");
        // return new AsyncResult<String>("task2 success");
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public Future<String> task3() throws Exception {
        logger.info("task3开始执行");
        Thread.sleep(3000);
        logger.info("task3执行结束");
        return new AsyncResult<String>("task3 success");
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public Future<String> task4() throws Exception {
        logger.info("task4开始执行");
        Thread.sleep(3000);
        logger.info("task4执行结束");
        return new AsyncResult<String>("task4 success");
    }
}

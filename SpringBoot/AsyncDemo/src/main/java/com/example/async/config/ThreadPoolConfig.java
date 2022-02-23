package com.example.async.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 线程池配置
 *
 * @author wliduo
 * @date 2019/2/15 14:36
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * logger
     */
    private final static Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Value("${asyncThreadPool.corePoolSize}")
    private int corePoolSize;

    @Value("${asyncThreadPool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${asyncThreadPool.queueCapacity}")
    private int queueCapacity;

    @Value("${asyncThreadPool.keepAliveSeconds}")
    private int keepAliveSeconds;

    @Value("${asyncThreadPool.awaitTerminationSeconds}")
    private int awaitTerminationSeconds;

    @Value("${asyncThreadPool.threadNamePrefix}")
    private String threadNamePrefix;

    /**
     * 线程池配置
     * @param
     * @return java.util.concurrent.Executor
     * @author wliduo
     * @date 2019/2/15 14:44
     */
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        logger.info("---------- 线程池开始加载 ----------");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程池大小
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        // 最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        // 队列容量
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        // 活跃时间
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        // 主线程等待子线程执行时间
        threadPoolTaskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        // 线程名字前缀
        threadPoolTaskExecutor.setThreadNamePrefix(threadNamePrefix);
        // RejectedExecutionHandler:当pool已经达到max-size的时候，如何处理新任务
        // CallerRunsPolicy:不在新线程中执行任务，而是由调用者所在的线程来执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        threadPoolTaskExecutor.initialize();
        logger.info("---------- 线程池加载完成 ----------");
        return threadPoolTaskExecutor;
    }

}

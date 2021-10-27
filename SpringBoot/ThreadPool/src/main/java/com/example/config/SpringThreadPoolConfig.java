package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Spring提供的封装了一层的线程池
 * 无法配置任务队列类型，无法动态调整任务队列长度
 *
 * @author wliduo
 * @date 2019/2/15 14:36
 */
@Configuration
public class SpringThreadPoolConfig {

    /**
     * logger
     */
    private final static Logger logger = LoggerFactory.getLogger(SpringThreadPoolConfig.class);

    @Value("${threadPool.corePoolSize}")
    private int corePoolSize;

    @Value("${threadPool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${threadPool.queueCapacity}")
    private int queueCapacity;

    @Value("${threadPool.keepAliveSeconds}")
    private int keepAliveSeconds;

    @Value("${threadPool.awaitTerminationSeconds}")
    private int awaitTerminationSeconds;

    @Value("${threadPool.springThreadNamePrefix}")
    private String threadNamePrefix;

    /**
     * 线程池配置
     *
     * @param
     * @return java.util.concurrent.Executor
     * @author wliduo
     * @date 2019/2/15 14:44
     */
    @Bean(name = "springThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        logger.info("---------- SpringThreadPool Start ----------");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程池大小
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        // 最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        // 队列容量，设置为小于等于0队列为SynchronousQueue，大于0为LinkedBlockingQueue
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        // 活跃时间
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        // 主线程等待子线程执行时间
        threadPoolTaskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        // 线程名字前缀
        threadPoolTaskExecutor.setThreadNamePrefix(threadNamePrefix);
        // 拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 初始化
        threadPoolTaskExecutor.initialize();
        logger.info("---------- SpringThreadPool End ----------");
        return threadPoolTaskExecutor;
    }

}

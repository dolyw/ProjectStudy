package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Jdk原生提供的线程池
 *
 * @author wliduo
 * @date 2019/2/15 14:36
 */
@Configuration
public class JdkThreadPoolConfig {

    /**
     * logger
     */
    private final static Logger logger = LoggerFactory.getLogger(JdkThreadPoolConfig.class);

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

    @Value("${threadPool.jdkThreadNamePrefix}")
    private String threadNamePrefix;

    @Value("${threadPool.ioThreadNamePrefix}")
    private String ioThreadNamePrefix;

    @Value("${threadPool.cpuThreadNamePrefix}")
    private String cpuThreadNamePrefix;

    /**
     * 线程池配置
     *
     * @param
     * @return java.util.concurrent.Executor
     * @author wliduo
     * @date 2019/2/15 14:44
     */
    @Bean(name = "jdkThreadPoolExecutor")
    public ThreadPoolExecutor jdkThreadPoolExecutor() {
        logger.info("---------- JdkThreadPool Start ----------");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveSeconds, TimeUnit.SECONDS,
                new ResizableCapacityLinkedBlockingQueue<>(queueCapacity),
                new CustomizableThreadFactory(threadNamePrefix),
                new ThreadPoolExecutor.CallerRunsPolicy());
        logger.info("---------- JdkThreadPool End ----------");
        return threadPoolExecutor;
    }

    /**
     * IO密集型线程池配置
     *
     * @param
     * @return java.util.concurrent.Executor
     * @author wliduo
     * @date 2019/2/15 14:44
     */
    @Bean(name = "ioThreadPoolExecutor")
    public ThreadPoolExecutor ioThreadPoolExecutor() {
        logger.info("---------- IoThreadPool Start ----------");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveSeconds, TimeUnit.SECONDS,
                new SynchronousQueue<>(false),
                new CustomizableThreadFactory(ioThreadNamePrefix),
                new ThreadPoolExecutor.AbortPolicy());
        logger.info("---------- IoThreadPool End ----------");
        return threadPoolExecutor;
    }

    /**
     * CPU(计算)密集型线程池配置
     *
     * @param
     * @return java.util.concurrent.Executor
     * @author wliduo
     * @date 2019/2/15 14:44
     */
    @Bean(name = "cpuThreadPoolExecutor")
    public ThreadPoolExecutor cpuThreadPoolExecutor() {
        logger.info("---------- CpuThreadPool Start ----------");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveSeconds, TimeUnit.SECONDS,
                new ResizableCapacityLinkedBlockingQueue<>(queueCapacity),
                new CustomizableThreadFactory(cpuThreadNamePrefix),
                new ThreadPoolExecutor.CallerRunsPolicy());
        logger.info("---------- CpuThreadPool End ----------");
        return threadPoolExecutor;
    }

}

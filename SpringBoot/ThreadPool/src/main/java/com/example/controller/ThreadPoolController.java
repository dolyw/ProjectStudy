package com.example.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.example.config.ResizableCapacityLinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * AsyncController
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/19 14:46
 */
@RestController
@RequestMapping("/pool")
public class ThreadPoolController {

    /**
     * logger
     */
    private final static Logger logger = LoggerFactory.getLogger(ThreadPoolController.class);

    @Autowired
    private Map<String, ThreadPoolExecutor> threadPoolExecutorMap = new ConcurrentHashMap<>(16);

    @Autowired
    private Map<String, ThreadPoolTaskExecutor> threadPoolTaskExecutorMap = new ConcurrentHashMap<>(16);

    /**
     * 线程池状态
     *
     * @param name
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/10/27 17:29
     */
    @GetMapping("/state")
    public String state(@RequestParam("name") String name) throws Exception {
        ThreadPoolExecutor threadPoolExecutor = null;
        if (SpringUtil.getBean(name) instanceof ThreadPoolTaskExecutor) {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = SpringUtil.getBean(name);
            threadPoolExecutor = threadPoolTaskExecutor.getThreadPoolExecutor();
        } else {
            threadPoolExecutor = SpringUtil.getBean(name);
        }
        CustomizableThreadFactory customizableThreadFactory = (CustomizableThreadFactory) threadPoolExecutor.getThreadFactory();
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("线程池名称: " + customizableThreadFactory.getThreadNamePrefix());
        stringBuffer.append("<br/>");
        stringBuffer.append("核心线程数: " + threadPoolExecutor.getCorePoolSize());
        stringBuffer.append("<br/>");
        stringBuffer.append("活动线程数:" + threadPoolExecutor.getActiveCount());
        stringBuffer.append("<br/>");
        stringBuffer.append("最大线程数:" + threadPoolExecutor.getMaximumPoolSize());
        stringBuffer.append("<br/>");
        stringBuffer.append("线程池活跃度(%):" + NumberUtil.div(threadPoolExecutor.getActiveCount(), threadPoolExecutor.getMaximumPoolSize()) * 100);
        stringBuffer.append("<br/>");
        stringBuffer.append("任务总数:" + threadPoolExecutor.getTaskCount());
        stringBuffer.append("<br/>");
        stringBuffer.append("任务完成数:" + threadPoolExecutor.getCompletedTaskCount());
        stringBuffer.append("<br/>");
        stringBuffer.append("队列类型:" + threadPoolExecutor.getQueue().getClass().getName());
        stringBuffer.append("<br/>");
        if (threadPoolExecutor.getQueue().size() + threadPoolExecutor.getQueue().remainingCapacity() > 0) {
            stringBuffer.append("队列大小:" + (threadPoolExecutor.getQueue().size() + threadPoolExecutor.getQueue().remainingCapacity()));
            stringBuffer.append("<br/>");
            stringBuffer.append("当前排队线程数:" + threadPoolExecutor.getQueue().size());
            stringBuffer.append("<br/>");
            stringBuffer.append("队列剩余大小:" + threadPoolExecutor.getQueue().remainingCapacity());
            stringBuffer.append("<br/>");
            stringBuffer.append("队列使用度(%):" + NumberUtil.div(threadPoolExecutor.getQueue().size(), threadPoolExecutor.getQueue().size() + threadPoolExecutor.getQueue().remainingCapacity()) * 100);
            stringBuffer.append("<br/>");
        }
        stringBuffer.append("线程活跃时间(秒):" + threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS));
        stringBuffer.append("<br/>");
        stringBuffer.append("拒绝策略:" + threadPoolExecutor.getRejectedExecutionHandler().getClass().getName());
        return stringBuffer.toString();
    }

    @Autowired
    private ThreadPoolExecutor jdkThreadPoolExecutor;

    /**
     * 调整线程池
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/10/27 17:32
     */
    @GetMapping("/set")
    public String set() throws Exception {
        jdkThreadPoolExecutor.setCorePoolSize(1);
        jdkThreadPoolExecutor.setMaximumPoolSize(2);
        jdkThreadPoolExecutor.setKeepAliveTime(120, TimeUnit.SECONDS);
        jdkThreadPoolExecutor.setThreadFactory(new CustomizableThreadFactory("jdk-thread-pool-new-"));
        jdkThreadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        ResizableCapacityLinkedBlockingQueue resizableCapacityLinkedBlockingQueue = (ResizableCapacityLinkedBlockingQueue) jdkThreadPoolExecutor.getQueue();
        // 设置队列长度
        resizableCapacityLinkedBlockingQueue.setCapacity(1);
        return "OK";
    }

}

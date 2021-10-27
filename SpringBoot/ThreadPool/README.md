# 线程池参数优化设置

> 线程池使用面临的核心的问题在于：线程池的参数并不好配置

**代码地址**

* Github: [https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/ThreadPool](https://github.com/dolyw/ProjectStudy/tree/master/SpringBoot/ThreadPool)
* Gitee(码云): [https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/ThreadPool](https://gitee.com/dolyw/ProjectStudy/tree/master/SpringBoot/ThreadPool)

## 1. 业务场景

* 快速响应用户请求
* 快速处理批量任务

### 1.1. 快速响应用户请求

从用户体验角度看，这个结果响应的越快越好，如果一个页面半天都刷不出，用户可能就放弃查看这个页面了。而面向用户的功能聚合通常非常复杂，伴随着调用与调用之间的级联、多级级联等情况，业务开发同学往往会选择使用线程池这种简单的方式，将调用封装成任务并行的执行，缩短总体响应时间

```java
ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
    corePoolSize,
    maxPoolSize,
    keepAliveSeconds, TimeUnit.SECONDS,
    new SynchronousQueue<>(false),
    new CustomizableThreadFactory(ioThreadNamePrefix),
    new ThreadPoolExecutor.AbortPolicy());
```

这种场景最重要的就是获取最大的响应速度去满足用户，所以应该不设置队列去缓冲并发任务，调高 corePoolSize 和 maxPoolSize 去尽可能创造多的线程快速执行任务，使用 SynchronousQueue 任务队列，支持公平锁和非公平锁，配合拒绝策略 AbortPolicy，到达最大线程直接抛出异常，达到最快响应

### 2.2. 快速处理批量任务

离线的大量计算任务，需要快速执行，与响应速度优先的场景区别在于，这类场景任务量巨大，并不需要瞬时的完成，而是关注如何使用有限的资源，尽可能在单位时间内处理更多的任务，也就是吞吐量优先的问题。所以应该设置队列去缓冲并发任务，调整合适的 corePoolSize 去设置处理任务的线程数。在这里，设置的线程数过多可能还会引发线程上下文切换频繁的问题，也会降低处理任务的速度，降低吞吐量

```java
ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
    corePoolSize,
    maxPoolSize,
    keepAliveSeconds, TimeUnit.SECONDS,
    new ResizableCapacityLinkedBlockingQueue<>(queueCapacity),
    new CustomizableThreadFactory(cpuThreadNamePrefix),
    new ThreadPoolExecutor.CallerRunsPolicy());
```

这个只能在测试中根据服务器资源平衡找到最优的配置，使用修改的 ResizableCapacityLinkedBlockingQueue，可以设定 capacity 参数，动态调整队列长度，在这里就不能使用拒绝策略的抛异常及丢弃任务等等，应该使用 CallerRunsPolicy，等待执行，当然也可以自定义拒绝策略，在策略执行失败日志记录，任务重新入库，重试执行几次等等

```java
/**
 * 自定义拒绝策略
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/27 18:18
 */
public static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // log("r rejected")
        // save r kafka mysql redis
        // try 3 times
        if(executor.getQueue().size() < 10000) {
            // try put again();
        }
    }
}
```

## 2. 动态化线程池

* 监控
* 调整

### 2.1. 监控

查看当前线程池状态

```java
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
```
```bash
线程池名称: jdk-thread-pool-
核心线程数: 15
活动线程数:10
最大线程数:30
线程池活跃度(%):33.333333329999995
任务总数:17
任务完成数:7
队列类型:com.example.config.ResizableCapacityLinkedBlockingQueue
队列大小:15
当前排队线程数:0
队列剩余大小:15
队列使用度(%):0.0
线程活跃时间(秒):60
拒绝策略:java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy
```

### 2.2. 调整

动态更新线程池参数

```java
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
```

**参考**

* [Java线程池实现原理及其在美团业务中的实践](https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html)
* [线程池的参数动态调整](https://www.cnblogs.com/root429/p/12799234.html)
* [并发和并行的区别](https://www.jianshu.com/p/cbf9588b2afb)
* [多核CPU 和多个 CPU 区别 并行和并发](https://blog.csdn.net/qq_38998213/article/details/87688929)
* [Java线程池工作原理](https://blog.csdn.net/summer_fish/article/details/109952529)


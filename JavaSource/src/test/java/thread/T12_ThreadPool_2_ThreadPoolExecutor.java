package thread;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * 自定义线程池 - ThreadPoolExecutor
 *
 * 自定义线程池很重要，如果线程池中的线程数量过多，就会竞争稀缺的处理器和内存资源，浪费大量时间在上下文切换，
 * 反之，线程数量过少，处理器的一个核就无法充分利用到(Java并发编程实战)
 *
 * N-Thread(线程数) = N-CPU * U-CPU * (1 + W/C)
 *
 * N-CPU: 处理器核的数目，可以通过Runtime.getRuntime().availableProcessors()获取
 * U-CPU: 期望的CPU利用率，0-1之前，CPU稳定在百分之多少，一般不可能为1(百分之百)
 * W/C: 等待时间与计算时间的比率
 *
 * 线程池6个参数
 * int corePoolSize: 核心线程数
 * int maximumPoolSize: 最大线程数
 * long keepAliveTime: 空闲线程存活时间
 * TimeUnit unit: 存活时间单位
 * BlockingQueue<Runnable> workQueue: 任务队列
 * ThreadFactory threadFactory: 线程工厂
 * RejectedExecutionHandler handler: 拒绝策略
 *
 * 1. Running的线程小于corePoolSize，直接创建新的线程在Pool执行
 * 2. Running的线程等于corePoolSize，则任务加入工作队列
 * 3. Running的线程等于corePoolSize，工作队列已满，则加入大于corePoolSize小于maximumPoolSize线程
 * 4. 全部满，执行拒绝策略
 *
 * 核心线程数: 线程池中会维护一个最小的线程数量，即使这些线程是空闲状态，他们也不会被销毁，
 * 除非设置了allowCoreThreadTimeOut
 *
 * 空闲线程存活时间: 这个只作用于核心线程之外的线程，除非设置了allowCoreThreadTimeOut
 *
 * 默认提供线程工厂
 * Executors.defaultThreadFactory()
 * Executors.privilegedThreadFactory()
 *
 * 默认提供拒绝策略
 * new ThreadPoolExecutor.AbortPolicy(): 直接丢弃任务，并抛出RejectedExecutionException异常
 * new ThreadPoolExecutor.DiscardPolicy(): 直接丢弃任务，什么都不做
 * new ThreadPoolExecutor.DiscardOldestPolicy(): 抛弃队列最早的那个任务，然后尝试把这次拒绝的任务放入队列
 * new ThreadPoolExecutor.CallerRunsPolicy(): 不在新线程中执行任务，而是由调用者所在的线程来执行，
 * 除非线程池已经isShutDown，则直接抛弃任务
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 17:17
 */
public class T12_ThreadPool_2_ThreadPoolExecutor {

    public static class Task implements Runnable {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Task " + name);
            try {
                // 每个线程阻塞
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{" + "no=" + name + '}';
        }
    }

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

    public static void main(String[] args) {
        // 创建线程池
        /*ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());*/
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                Executors.privilegedThreadFactory(),
                new CustomRejectedExecutionHandler());
        // 启动8个任务
        for (Integer i = 0; i < 8; i++) {
            threadPoolExecutor.execute(new Task(i.toString()));
        }
        // 输出当前线程池任务队列
        System.out.println(threadPoolExecutor.getQueue());
        // 再启动一个任务，超过了最大核心线程数+队列数(4+4=8)，第9个任务将执行拒绝策略
        threadPoolExecutor.execute(new Task("M"));
        // 关闭线程池
        threadPoolExecutor.shutdown();
    }

}

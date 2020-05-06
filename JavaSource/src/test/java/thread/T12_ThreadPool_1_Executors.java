package thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程池 - Executor接口，ExecutorService接口，Executors类
 *
 * Executors类里默认提供了一些线程池，不过都不推荐使用，推荐自定义
 *
 * isShutDown: 当调用shutdown()或shutdownNow()方法后返回为true
 * isTerminated: 当调用shutdown()方法后，并且所有提交的任务完成后返回为true
 *
 * newSingleThreadExecutor和newFixedThreadPool: 默认提供的线程池使用的任务队列是LinkedBlockingQueue，
 * 构造方法默认大小是Integer.MAX_VALUE，堆积的请求处理队列可能会耗费非常大的内存
 *
 * newCachedThreadPool: 核心线程数是0，最大线程数是Integer.MAX_VALUE，可能会创建数量非常多的线程，
 * 任务队列使用的SynchronousQueue
 *
 * newScheduledThreadPool: 支持定时以及周期性执行任务，核心线程数需要指定，
 * 最大线程数是Integer.MAX_VALUE，可能会创建数量非常多的线程，任务队列使用的DelayedWorkQueue
 *
 * SingleThreadPool和FixedThreadPool允许的请求队列长度为Integer.MAX_VALUE，
 * 可能会堆积大量的请求，从而导致OOM
 *
 * CachedThreadPool允许的创建线程数量为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 17:17
 */
public class T12_ThreadPool_1_Executors {

    public static class CustomExecutor implements Executor {

        @Override
        public void execute(Runnable command) {
            // new Thread(command).run();
            command.run();
        }
    }

    /**
     * Executor接口和ExecutorService接口
     *
     * @param
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/4/27 17:14
     */
    public static void executorAndExecutorService() {
        // Executor接口
        new CustomExecutor().execute(() -> System.out.println("Hello Executor"));
        // 官方默认提供的线程池newFixedThreadPool
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 线程池启动6个线程
        for (int i = 0; i < 6; i++) {
            executorService.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(555);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(executorService);
        // 停止线程池
        executorService.shutdown();
        System.out.println(executorService.isTerminated());
        System.out.println(executorService.isShutdown());
        System.out.println(executorService);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(executorService.isTerminated());
        System.out.println(executorService.isShutdown());
        System.out.println(executorService);
    }

    /**
     * 单线程的线程池newSingleThreadExecutor
     * 任务队列LinkedBlockingQueue不赋值，构造方法默认大小Integer.MAX_VALUE
     * 无法自定义拒绝策略
     * 堆积的请求处理队列可能会耗费非常大的内存
     *
     * @param
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/4/27 17:04
     */
    public static void newSingleThreadExecutor() {
        System.out.println("-----newSingleThreadExecutor-----");
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            final int j = i;
            singleThreadExecutor.execute(() -> {
                System.out.println(j + " " + Thread.currentThread().getName());
            });
        }
        singleThreadExecutor.shutdown();
    }

    /**
     * 固定线程池newFixedThreadPool
     * 核心线程数和最大线程数必须赋值，而且一样
     * 任务队列LinkedBlockingQueue不赋值，构造方法默认大小Integer.MAX_VALUE
     * 无法自定义拒绝策略
     * 堆积的请求处理队列可能会耗费非常大的内存
     *
     * @param
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/4/27 17:04
     */
    public static void newFixedThreadPool() {
        System.out.println("-----newFixedThreadPool-----");
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            final int j = i;
            fixedThreadPool.execute(() -> {
                System.out.println(j + " " + Thread.currentThread().getName());
            });
        }
        fixedThreadPool.shutdown();
    }

    /**
     * 缓存线程池newCachedThreadPool
     * 核心线程数是0，最大线程数是Integer.MAX_VALUE，可能会创建数量非常多的线程
     * 任务队列使用的SynchronousQueue
     * 无法自定义拒绝策略
     *
     * @param
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/4/27 17:04
     */
    public static void newCachedThreadPool() {
        System.out.println("-----newCachedThreadPool-----");
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        System.out.println(cachedThreadPool);
        for (int i = 0; i < 2; i++) {
            cachedThreadPool.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(499);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(cachedThreadPool);
        try {
            TimeUnit.SECONDS.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cachedThreadPool);
    }

    /**
     * newScheduledThreadPool线程池支持定时以及周期性执行任务
     * 核心线程数需要指定，最大线程数是Integer.MAX_VALUE，可能会创建数量非常多的线程
     * 任务队列使用的DelayedWorkQueue
     * 无法自定义拒绝策略
     *
     * @param
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/4/27 18:06
     */
    public static void newScheduledThreadPool() {
        System.out.println("-----newScheduledThreadPool-----");
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);
        scheduledThreadPool.scheduleAtFixedRate(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        // executorAndExecutorService();
        // newSingleThreadExecutor();
        // newCachedThreadPool();
        newFixedThreadPool();
        // newScheduledThreadPool();
    }

}

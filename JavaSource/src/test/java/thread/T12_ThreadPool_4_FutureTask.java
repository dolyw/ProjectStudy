package thread;

import java.util.concurrent.*;

/**
 * 带返回值的异步线程 - FutureTask - 继承Runnable, Future
 * 使用FutureTask声明结果值，可以直接用execute()执行
 *
 * 还有一种使用，Guava提供了FutureCallback接口，可以在成功或失败时回调处理，但是代码不太好维护
 * 还不如直接拿到结果过进行处理就是
 * https://github.com/bjmashibing/JUC/blob/master/src/main/java/com/mashibing/juc/c_027_future_to_loom/T02_ListenableFuture.java
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 17:17
 */
public class T12_ThreadPool_4_FutureTask {

    public static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            return "Hello Callable";
        }
    }

    public static void main(String[] args) {
        // 线程执行
        FutureTask<String> futureTaskTemp = new FutureTask<String>(() -> {
            return "Hello FutureTask";
        });
        new Thread(futureTaskTemp).start();
        try {
            System.out.println(futureTaskTemp.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        // FutureTask声明任务
        FutureTask<String> futureTask = new FutureTask<>(new Task());
        // 异步提交任务execute也行
        threadPoolExecutor.execute(futureTask);
        System.out.println("Start");
        try {
            // 阻塞
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("End");
        // 关闭线程池
        threadPoolExecutor.shutdown();
    }

}

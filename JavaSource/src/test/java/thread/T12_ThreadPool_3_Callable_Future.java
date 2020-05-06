package thread;

import java.util.concurrent.*;

/**
 * 带返回值的异步线程 - Callable
 *
 * 使用submit提交Future进行接收返回值，future.get()阻塞获取返回结果
 *
 * future.get()还可以设置阻塞时间，超过了直接抛出TimeoutException异常
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 17:17
 */
public class T12_ThreadPool_3_Callable_Future {

    public static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            return "Hello Callable";
        }
    }

    public static void main(String[] args) {
        // 创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        // 异步提交任务
        Future<String> future = threadPoolExecutor.submit(new Task());
        System.out.println("Start");
        try {
            // 阻塞
            // System.out.println(future.get());
            // 阻塞多久超时
            System.out.println(future.get(500, TimeUnit.MILLISECONDS));
        } catch (TimeoutException e) {
            e.printStackTrace();
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

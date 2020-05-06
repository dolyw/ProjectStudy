package thread;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * WorkStealingPool - JDK1.8新增newWorkStealingPool默认线程池
 * https://blog.csdn.net/tjbsl/article/details/98480843
 *
 * 使用多个Work Queue，采用Work Stealing算法，多个线程在执行的时候，线程1执行完了，
 * 会自动去拿一些别的线程的任务来执行，分担别的线程的任务
 *
 * 底层使用的是ForkJoinPool
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 17:17
 */
public class T12_ThreadPool_6_WorkStealingPool {

    public static class R implements Runnable {
        int time;

        R(int t) {
            this.time = t;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(time + " " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());
        service.execute(new R(1000));
        service.execute(new R(2000));
        service.execute(new R(2000));
        // daemon
        service.execute(new R(2000));
        service.execute(new R(2000));
        // 由于产生的是精灵线程（守护线程、后台线程），主线程不阻塞的话，看不到输出
        System.in.read();
    }

}

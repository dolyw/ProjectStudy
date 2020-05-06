package thread;

import java.util.concurrent.Semaphore;

/**
 * Semaphore - 底层使用的AQS
 * 计数信号量 - 常用于限制可以访问某些资源的线程数量(控制线程的并发数量)，例如限流
 * https://www.cnblogs.com/klbc/p/9500947.html
 *
 * 下面代码中
 * 在semaphore.acquire()和semaphore.release()之间的代码，同一时刻只允许制定个数的线程进入
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 10:45
 */
public class T09_JUC_8_Semaphore {

    public static void main(String[] args) {
        // 参数只允许一个线程同时执行，并且是公平锁
        Semaphore semaphore = new Semaphore(1, true);
        new Thread(() -> {
            try {
                // 开始
                semaphore.acquire();
                System.out.println("t1 start");
                Thread.sleep(1000);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放
                semaphore.release();
            }
        }).start();
        new Thread(() -> {
            try {
                // 开始
                semaphore.acquire();
                System.out.println("t2 start");
                Thread.sleep(1000);
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放
                semaphore.release();
            }
        }).start();
        new Thread(() -> {
            try {
                // 开始
                semaphore.acquire();
                System.out.println("t3 start");
                Thread.sleep(1000);
                System.out.println("t3 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 释放
                semaphore.release();
            }
        }).start();
    }

}

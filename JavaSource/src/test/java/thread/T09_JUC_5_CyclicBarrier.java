package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier - 底层使用的ReentrantLock
 * 循环栅栏 - 给定一个线程数，参与线程执行到了这个数量就执行特定方法
 * https://www.jianshu.com/p/333fd8faa56e
 *
 * 一个线程组的线程需要等待所有线程完成任务后再继续执行下一次任务
 * 比如同时三个线程去读取数据，必须这三个线程读取完了才能把三个线程的数据合并为一个文件
 *
 * CyclicBarrier与CountDownLatch 区别
 * CountDownLatch是一次性的，CyclicBarrier是可循环利用的
 * CountDownLatch参与的线程的职责是不一样的，有的在倒计时，有的在等待倒计时结束
 * CyclicBarrier参与的线程职责是一样的
 *
 * 下面代码使用CyclicBarrier设定20个线程一次执行特定方法输出20个线程了
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/20 18:50
 */
public class T09_JUC_5_CyclicBarrier {

    public static void main(String[] args) {
        // CyclicBarrier barrier = new CyclicBarrier(20);
        CyclicBarrier barrier = new CyclicBarrier(20,
                () -> System.out.println("20个线程了"));
        /*CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("20个线程了");
            }
        });*/

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
            System.out.println(i);
        }
    }

}

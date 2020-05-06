package containers;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * LinkedBlockingQueue - 无大小限制
 *
 * 生产者和消费者模型
 *
 * 下面代码1个生产者，5个消费者
 *
 * BlockingQueue(阻塞队列)比Queue多了两个方法put()和take()
 *
 * put()往队列放，如果满了，就会等待
 * take()往队列取，如果空了，就会等待
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T04_BlockingQueue_1_LinkedBlockingQueue {

    public static BlockingQueue<String> strings = new LinkedBlockingQueue<>();

    public static Random r = new Random();

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    // 如果满了，就会等待
                    strings.put("a" + i);
                    System.out.println(Thread.currentThread().getName() + " put a" + i);
                    TimeUnit.MILLISECONDS.sleep(r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "p").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for ( ; ; ) {
                    try {
                        // 如果空了，就会等待
                        System.out.println(Thread.currentThread().getName() + " take " + strings.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "c" + i).start();
        }
    }

}

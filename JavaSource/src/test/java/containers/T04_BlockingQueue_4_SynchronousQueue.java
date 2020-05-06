package containers;

import java.util.concurrent.*;

/**
 * SynchronousQueue - 容量为0 - 一个线程对一个线程，必须有其他线程在消费，手递手
 *
 * 一般都是put()，take()使用，本身无法存储，一直输出
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T04_BlockingQueue_4_SynchronousQueue {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strings = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(strings.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 阻塞等待消费者消费
        strings.put("aaa");
        // strings.put("bbb");
        // add直接异常，offer加不进，因为容量为0
        // strings.add("aaa");
        System.out.println(strings.size());
    }

}

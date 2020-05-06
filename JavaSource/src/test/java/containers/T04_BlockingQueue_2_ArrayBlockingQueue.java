package containers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue - 有大小限制 - 指定大小
 *
 * put()往队列放，如果满了，就会等待
 * add()往队列放，如果满了，就会抛异常
 * offer()往队列放，如果满了，就会返回false
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T04_BlockingQueue_2_ArrayBlockingQueue {

    public static BlockingQueue<String> strings = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try {
                // 加满
                strings.put("a" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 满了就会等待，程序阻塞
        /*try {
            strings.put("aaa");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        // 满了抛异常
        // strings.add("aaa");
        // 满了返回false
        // System.out.println(strings.offer("aaa"));

        try {
            // 等待1S加，会阻塞，超过时间没加上就返回false
            System.out.println(strings.offer("aaa", 1, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(strings);
    }

}

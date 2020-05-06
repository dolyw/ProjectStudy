package containers;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ConcurrentLinkedQueue
 *
 * 多线程下线程安全，
 * 多线程下，先考虑使用Queue，再考虑List，Set
 *
 * offer()在添加元素时，如果发现队列已满无法添加的话，会直接返回false
 * add()在添加元素的时候，一些队列有大小限制，若超出了度列的长度会直接抛出异常
 *
 * poll()删除返回第一个数据，但是在用空队列调用时不是抛出异常，只是返回null
 * remove()删除返回到第一个数据，用空队列调用时抛出异常
 *
 * peek()获取到第一个数据，不会删除，但是在用空队列调用时不是抛出异常，只是返回null
 * element()获取到第一个数据，不会删除，用空队列调用时抛出异常
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T03_Queue_1_ConcurrentLinkedQueue {

    public static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("编号：" + i);
        }
    }

    public static void main(String[] args) {
        // 简单使用
        Queue<String> strings = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < 10; i++) {
            strings.offer("a" + i);
        }

        System.out.println(strings);
        System.out.println(strings.size());

        // poll()获删除返回第一个数据
        System.out.println(strings.poll());
        System.out.println(strings.size());

        // remove()删除返回第一个数据
        System.out.println(strings.remove());
        System.out.println(strings.size());

        // peek()取到第一个数据，不会删除
        System.out.println(strings.peek());
        System.out.println(strings.size());

        // element()取到第一个数据，不会删除
        System.out.println(strings.element());
        System.out.println(strings.size());


        // 抢票实现

        /*for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // poll是一个原子操作，这里每次都直接取出来给了ticket变量，
                // 后面的代码就是每个线程获取自己的票直接卖了，所以这里代码是线程安全的
                while (true) {
                    String ticket = tickets.poll();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (ticket == null) {
                        break;
                    } else {
                        System.out.println("销售了票--" + ticket);
                    }
                }
            }).start();
        }*/
    }

}

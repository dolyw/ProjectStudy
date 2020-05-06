package interview;

import java.util.LinkedList;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 为什么用while，不用if
 * 因为进入wait()等待着，如果醒了继续往下执行应该再判断是不是strings.size() == MAX_SIZE队列已经满了
 * 所以用if的话会出现超过最大容量继续加元素的情况
 * 同理，get方法里，如果用if，会出现大小为0的时候还去removeFirst()就异常了
 *
 * 这种方式比第一种性能好些
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/24 9:38
 */
public class T02_1_sync_wait_notify_2 {

    public static class MyContainer<T> {

        private final LinkedList<T> strings = new LinkedList<>();

        // 最大容量
        private static final int MAX_SIZE = 5;

        // count
        private int count = 0;

        public synchronized void put(T s) {
            // 为什么用while，不用if
            while (strings.size() == MAX_SIZE) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            strings.add(s);
            ++count;
            System.out.println(Thread.currentThread().getName());
            this.notifyAll();
        }

        public synchronized T get() {
            // 为什么用while，不用if
            while (strings.size() == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T s = strings.removeFirst();
            count--;
            System.out.println(Thread.currentThread().getName());
            this.notifyAll();
            return s;
        }

        public synchronized int getCount() {
            return count;
        }

    }

    public static void main(String[] args) {
        MyContainer<Object> myContainer = new MyContainer();
        // 2个生产者，1个生产者生产10个元素
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myContainer.put(new Object());
                }
            }, "p" + i).start();
        }
        // 10个消费者，1个消费者消费2个元素
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myContainer.get();
                }
            }, "c" + i).start();
        }
    }

}

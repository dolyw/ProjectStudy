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
 * 但是这种写法性能很差，看T02_1_sync_wait_notify_2
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/24 9:38
 */
public class T02_1_sync_wait_notify_1 {

    public static class MyContainer {

        public LinkedList list = new LinkedList();

        // 最大容量
        private static final int MAX_SIZE = 5;

        public void put(Object o) {
            list.add(o);
        }

        public Object get() {
            return list.removeFirst();
        }

        public int getCount() {
            return list.size();
        }

        public static void main(String[] args) {
            MyContainer myContainer = new MyContainer();
            final Object object = new Object();
            // 2个生产者，1个生产者生产10个元素
            for (int i = 0; i < 2; i++) {
                new Thread(() -> {
                    for (int j = 0; j < 10; j++) {
                        synchronized (object) {
                            // 为什么用while，不用if
                            while (myContainer.getCount() == MAX_SIZE) {
                                try {
                                    // 等待
                                    object.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            myContainer.put(new Object());
                            System.out.println(Thread.currentThread().getName());
                            // 通知t1执行
                            object.notifyAll();
                        }
                    }
                }, "p" + i).start();
            }
            // 10个消费者，1个消费者消费2个元素
            for (int i = 0; i < 10; i++) {
                new Thread(() -> {
                    for (int j = 0; j < 2; j++) {
                        synchronized (object) {
                            // 为什么用while，不用if
                            while (myContainer.getCount() <= 0) {
                                try {
                                    object.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            myContainer.get();
                            System.out.println(Thread.currentThread().getName());
                            // 通知t1执行
                            object.notifyAll();
                        }
                    }
                }, "c" + i).start();
            }
        }

    }

}

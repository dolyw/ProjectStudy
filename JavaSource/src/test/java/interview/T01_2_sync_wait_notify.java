package interview;

import java.util.ArrayList;
import java.util.List;

/**
 * (淘宝)实现一个容器，提供两个方法，add，size，
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 主要考的是线程的通信，之前采用volatile，这里我们使用wait和notify
 *
 * t2先启动wait等着，t1启动开始添加元素，到第5个时notify通知t2执行，并且wait自身等待，
 * t2开始执行完成再通知t1可以继续执行了
 *
 * wait会释放锁，而notify不会释放锁，所以notify之后，t1必须释放锁，t2退出后，也必须notify通知t1继续执行
 * 整个通信过程比较繁琐
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 14:15
 */
public class T01_2_sync_wait_notify {

    public static class InterviewTest {

        public List list = new ArrayList<>();

        public void add(Object o) {
            list.add(o);
        }

        public int size() {
            return list.size();
        }

        public static void main(String[] args) {
            InterviewTest interviewTest = new InterviewTest();
            final Object object = new Object();
            // 先起一个线程等待
            new Thread(() -> {
                synchronized (object) {
                    if (interviewTest.size() != 5) {
                        try {
                            // 等待
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("结束");
                    // 通知t1执行
                    object.notify();
                }
            }, "t2").start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 再起一个线程装10个元素，元素大小到了5就通知线程t2
            new Thread(() -> {
                synchronized (object) {
                    for (int i = 0; i < 10; i++) {
                        interviewTest.add(new Object());
                        System.out.println(i);
                        if (interviewTest.size() == 5) {
                            // 只有两个线程，notify和notifyAll一样，通知t2执行
                            object.notify();
                            // 等待t2执行完成，t2再通知回来
                            try {
                                object.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "t1").start();
        }

    }

}

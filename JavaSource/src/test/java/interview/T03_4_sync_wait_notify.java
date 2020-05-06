package interview;

import java.util.LinkedList;
import java.util.concurrent.locks.LockSupport;

/**
 * 要求用两个线程顺序交替打印1-26，A-Z，例如A1B2C3....Z26
 *
 * 主要考的是线程的通信，这里我们使用synchronized + wait/notify
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 14:15
 */
public class T03_4_sync_wait_notify {

    public static class InterviewTest {

        public static char[] aI = "123456789".toCharArray();

        public static char[] aC = "ABCDEFGHI".toCharArray();

        public static void main(String[] args) {
            final Object object = new Object();

            new Thread(() -> {
                synchronized (object) {
                    for (char c : aC) {
                        System.out.print(c);
                        object.notify();
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    object.notify();
                }
            }, "t1").start();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(() -> {
                synchronized (object) {
                    for (char i : aI) {
                        System.out.print(i);
                        try {
                            object.notify();
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    object.notify();
                }
            }, "t2").start();
        }

    }

}

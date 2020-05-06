package interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * (淘宝)实现一个容器，提供两个方法，add，size，
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 主要考的是线程的通信，之前采用wait和notify，这里我们使用LockSupport
 *
 * 使用LockSupport替代wait，notify来进行通知
 * 好处是通信方式简单，使用park和unpark方法替代wait和notify
 * 当不涉及同步，只是涉及线程通信的时候，用synchronized + wait/notify就显得太重了
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 14:15
 */
public class T01_4_LockSupport {

    public static class InterviewTest {

        // volatile修饰，线程可见
        public volatile List list = new ArrayList<>();

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
            Thread t2 = new Thread(() -> {
                if (interviewTest.size() != 5) {
                    // 等待
                    LockSupport.park();
                }
                System.out.println("结束");
            }, "t2");
            t2.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 再起一个线程装10个元素，元素大小到了5就通知线程t2
            Thread t1 = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    synchronized (object) {
                        interviewTest.add(new Object());
                        System.out.println(i);
                        if (interviewTest.size() == 5) {
                            // 门栓打开
                            LockSupport.unpark(t2);
                        }
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t1");
            t1.start();
        }

    }

}

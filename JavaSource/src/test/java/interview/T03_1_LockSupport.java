package interview;

import java.util.concurrent.locks.LockSupport;

/**
 * 要求用两个线程顺序交替打印1-26，A-Z，例如A1B2C3....Z26
 *
 * 主要考的是线程的通信，这里我们使用LockSupport
 *
 * 使用LockSupport替代wait，notify来进行通知
 * 好处是通信方式简单，使用park和unpark方法替代wait和notify
 * 当不涉及同步，只是涉及线程通信的时候，用synchronized + wait/notify就显得太重了
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 14:15
 */
public class T03_1_LockSupport {

    public static class InterviewTest {

        public static char[] aI = "123456789".toCharArray();

        public static char[] aC = "ABCDEFGHI".toCharArray();

        public static Thread t1, t2 = null;

        public static void main(String[] args) {

            t1 = new Thread(() -> {
                for (char c : aC) {
                    System.out.print(c);
                    // t2门栓打开
                    LockSupport.unpark(t2);
                    // t1等待
                    LockSupport.park();
                }
            }, "t1");
            t1.start();

            t2 = new Thread(() -> {
                for (char i : aI) {
                    // t2等待
                    LockSupport.park();
                    System.out.print(i);
                    // t1门栓打开
                    LockSupport.unpark(t1);
                }
            }, "t2");
            t2.start();
        }

    }

}

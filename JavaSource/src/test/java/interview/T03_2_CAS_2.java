package interview;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 要求用两个线程顺序交替打印1-26，A-Z，例如A1B2C3....Z26
 *
 * 主要考的是线程的通信，这里我们使用CAS自旋的方式，用AtomicInteger
 *
 * 用while (atomicInteger.get() != 1) {}阻塞线程
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 14:15
 */
public class T03_2_CAS_2 {

    public static class InterviewTest {

        public static AtomicInteger atomicInteger = new AtomicInteger(1);

        public static char[] aI = "123456789".toCharArray();

        public static char[] aC = "ABCDEFGHI".toCharArray();

        public static void main(String[] args) {

            new Thread(() -> {
                for (char c : aC) {
                    while (atomicInteger.get() != 1) {}
                    System.out.print(c);
                    atomicInteger.set(2);
                }
            }, "t1").start();

            new Thread(() -> {
                for (char i : aI) {
                    while (atomicInteger.get() != 2) {}
                    System.out.print(i);
                    atomicInteger.set(1);
                }
            }, "t2").start();
        }

    }

}

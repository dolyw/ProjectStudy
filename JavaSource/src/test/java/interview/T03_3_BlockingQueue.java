package interview;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 要求用两个线程顺序交替打印1-26，A-Z，例如A1B2C3....Z26
 *
 * 主要考的是线程的通信，这里我们使用BlockingQueue的方式来阻塞
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 14:15
 */
public class T03_3_BlockingQueue {

    public static class InterviewTest {

        public static ArrayBlockingQueue arrayBlockQueue1 = new ArrayBlockingQueue(1);

        public static ArrayBlockingQueue arrayBlockQueue2 = new ArrayBlockingQueue(1);

        public static char[] aI = "123456789".toCharArray();

        public static char[] aC = "ABCDEFGHI".toCharArray();

        public static void main(String[] args) {

            new Thread(() -> {
                for (char c : aC) {
                    System.out.print(c);
                    try {
                        arrayBlockQueue1.put("ok");
                        arrayBlockQueue2.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t1").start();

            new Thread(() -> {
                for (char i : aI) {
                    try {
                        arrayBlockQueue1.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print(i);
                    try {
                        arrayBlockQueue2.put("ok");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t2").start();
        }

    }

}

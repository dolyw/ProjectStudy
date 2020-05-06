package interview;

/**
 * 要求用两个线程顺序交替打印1-26，A-Z，例如A1B2C3....Z26
 *
 * 主要考的是线程的通信，这里我们使用CAS自旋的方式，用枚举
 *
 * 用while (readyToRun != ReadyToRun.T1) {}阻塞线程
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 14:15
 */
public class T03_2_CAS_1 {

    public static class InterviewTest {

        public enum ReadyToRun {T1, T2}

        public volatile static ReadyToRun readyToRun = ReadyToRun.T1;

        public static char[] aI = "123456789".toCharArray();

        public static char[] aC = "ABCDEFGHI".toCharArray();

        public static void main(String[] args) {

            new Thread(() -> {
                for (char c : aC) {
                    while (readyToRun != ReadyToRun.T1) {}
                    System.out.print(c);
                    readyToRun = ReadyToRun.T2;
                }
            }, "t1").start();

            new Thread(() -> {
                for (char i : aI) {
                    while (readyToRun != ReadyToRun.T2) {}
                    System.out.print(i);
                    readyToRun = ReadyToRun.T1;
                }
            }, "t2").start();
        }

    }

}

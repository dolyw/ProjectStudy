package interview;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 要求用两个线程顺序交替打印1-26，A-Z，例如A1B2C3....Z26
 *
 * 主要考的是线程的通信，这里我们使用IO流
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 14:15
 */
public class T03_7_PipedStream {

    public static void main(String[] args) throws Exception {
        char[] aI = "123456789".toCharArray();
        char[] aC = "ABCDEFGHI".toCharArray();

        PipedInputStream input1 = new PipedInputStream();
        PipedInputStream input2 = new PipedInputStream();
        PipedOutputStream output1 = new PipedOutputStream();
        PipedOutputStream output2 = new PipedOutputStream();

        input1.connect(output2);
        input2.connect(output1);

        String msg = "Your Turn";

        new Thread(() -> {
            byte[] buffer = new byte[9];
            try {
                for (char c : aI) {
                    input1.read(buffer);

                    if (new String(buffer).equals(msg)) {
                        System.out.print(c);
                    }
                    output1.write(msg.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            byte[] buffer = new byte[9];
            try {
                for (char c : aC) {
                    System.out.print(c);
                    output2.write(msg.getBytes());
                    input2.read(buffer);
                    if (new String(buffer).equals(msg)) {
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }

}

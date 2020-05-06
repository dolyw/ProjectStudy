package thread;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport - 线程阻塞工具类 - 底层Unsafe的native方法
 * https://www.jianshu.com/p/1f16b838ccd8
 * https://www.jianshu.com/p/f1f2cd289205
 *
 * park()和unpark()可以实现类似wait()和notify()的功能，但是并不和wait()和notify()交叉，
 * 也就是说unpark()不会对wait()起作用，notify()也不会对park()起作用
 *
 * park()和unpark()的使用不会出现死锁的情况
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 10:45
 */
public class T09_JUC_10_LockSupport {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            // 每次输出停止500ms，到5的时候park停止
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if (i == 5) {
                    LockSupport.park();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        // 停止5s后unpark线程t，让t继续执行
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t);
    }

}

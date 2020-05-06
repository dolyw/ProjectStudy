package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock - 底层使用的AQS
 *
 * 下面代码使用ReentrantLock锁代替了synchronized锁
 * 使用ReentrantLock需要手动释放锁，再下面使用公平锁和非公平锁交替执行
 *
 * 使用synchronized锁定的话如果遇到异常，JVM会自动释放锁，但是Lock必须手动释放锁，因此要在finally中进行锁的释放
 *
 * 公平锁能保证：老的线程排队使用锁，新线程仍然排队使用锁
 * 非公平锁保证：老的线程排队使用锁，但是无法保证新线程抢占已经在排队的线程的锁
 * https://blog.csdn.net/m47838704/article/details/80013056
 * https://www.jianshu.com/p/2ada27eee90b
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/20 11:25
 */
public class T09_JUC_1_ReentrantLock {

    private static class ReentrantLockTest {
        // 构造参数为true为公平锁，默认为非公平锁
        public Lock lock = new ReentrantLock();

        public void m1() {
            try {
                lock.lock();
                for (int i = 0; i < 5; i++) {
                    System.out.println(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }
        }

        public void m2() {
            try {
                lock.lock();
                System.out.println("m2");
            } finally {
                lock.unlock();
            }
        }

        public static void main(String[] args) {
            ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
            // 启动一个线程执行m1
            new Thread(reentrantLockTest::m1).start();
            // 启动一个线程执行m2
            new Thread(reentrantLockTest::m2).start();
        }

    }

    public static void main(String[] args) {
        // 构造参数为true为公平锁，默认为非公平锁
        Lock lock = new ReentrantLock(true);
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    lock.lock();
                    System.out.println("t1 " + i);
                } finally {
                    lock.unlock();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    lock.lock();
                    System.out.println("t2 " + i);
                } finally {
                    lock.unlock();
                }
            }
        }).start();
    }

}

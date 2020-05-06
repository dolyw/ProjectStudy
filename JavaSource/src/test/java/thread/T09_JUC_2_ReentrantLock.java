package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock - 底层使用的AQS
 *
 * 下面代码使用ReentrantLock锁代替了synchronized锁
 * 使用ReentrantLock需要手动释放锁
 *
 * 使用synchronized锁定的话如果遇到异常，JVM会自动释放锁，但是Lock必须手动释放锁，因此要在finally中进行锁的释放
 *
 * 还可以使用tryLock进行尝试锁定，不管锁定与否，方法都将继续执行
 * 可以根据tryLock的返回值来判定是否锁定
 * 也可以指定tryLock的时间，由于tryLock(time)抛出异常，所以要注意unclock的处理，必须放到finally中
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/20 11:25
 */
public class T09_JUC_2_ReentrantLock {

    private static class ReentrantLockTest {
        // 构造参数为true为公平锁，默认为非公平锁
        public Lock lock = new ReentrantLock(true);

        public void m1() {
            try {
                lock.lock();
                for (int i = 5; i < 10; i++) {
                    System.out.println("m1 " + i);
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
            boolean lockMark = Boolean.FALSE;
            try {
                // 使用tryLock进行尝试锁定，3s内一直尝试获取锁，获取到直接往下执行，
                // 超过3s没获取到也往下执行
                lockMark = lock.tryLock(3, TimeUnit.SECONDS);
                if (lockMark) {
                    System.out.println("m2 Lock");
                } else {
                    System.out.println("m2 NotLock");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 锁定了才进行释放
                if (lockMark) {
                    lock.unlock();
                }
            }
        }

        public void m3() {
            // 尝试获取锁
            if (lock.tryLock()) {
                try {
                    // 拿到锁
                    System.out.println("m3 Lock");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } else {
                // 不能获取锁
                System.out.println("m3 NotLock");
            }
        }

        public static void main(String[] args) {
            ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
            // 启动一个线程执行m1
            new Thread(reentrantLockTest::m1).start();
            // 启动一个线程执行m2
            new Thread(reentrantLockTest::m2).start();
            // 启动一个线程执行m3
            new Thread(reentrantLockTest::m3).start();
        }

    }

}

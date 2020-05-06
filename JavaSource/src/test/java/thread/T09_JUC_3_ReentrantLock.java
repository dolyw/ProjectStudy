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
 * 使用synchronized锁定的话如果遇到异常，JVM会自动释放锁
 * 但是Lock必须手动释放锁，因此要在finally中进行锁的释放
 *
 * lockInterruptibly() - 待补充
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/20 15:36
 */
public class T09_JUC_3_ReentrantLock {

    private static class ReentrantLockTest {

        public static void main(String[] args) {
            Lock lock = new ReentrantLock();
            // 起一个线程一直等待
            Thread t1 = new Thread(() -> {
                try {
                    lock.lock();
                    System.out.println("t1 start");
                    TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                    System.out.println("t1 end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("t1 interrupt");
                } finally {
                    lock.unlock();
                }
            });
            t1.start();
            // 再起一个线程打断t1
            Thread t2 = new Thread(() -> {
                try {
                    // 强制打断拿到锁的线程，并且获取锁
                    lock.lockInterruptibly();
                    System.out.println("t2 start");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("t2 end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("t2 interrupt");
                } finally {
                    lock.unlock();
                }
            });
            t2.start();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t2.interrupt();
        }

    }

}

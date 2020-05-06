package thread;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock - 底层使用的AQS
 * 读写锁 - Synchronized存在明显的一个性能问题就是读与读之间互斥，简言之就是，
 * 可以做到读和读互不影响，读和写互斥，写和写互斥，提高读写效率
 * https://www.jianshu.com/p/9cd5212c8841
 *
 * 下面代码18个读取线程，2个写入线程
 * 使用reentrantLock执行的话读与读也存在互斥，执行了近10S
 * 而使用ReadWriteLock，读与读没有互斥，只需要1S
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 10:11
 */
public class T09_JUC_7_ReadWriteLock {

    public static ReentrantLock reentrantLock = new ReentrantLock();

    public static int value = 0;

    public static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static Lock readLock = readWriteLock.readLock();

    public static Lock writeLock = readWriteLock.writeLock();

    public static void read(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(499);
            System.out.println(Thread.currentThread().getName() + ":" + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public static void write(Lock lock, int v) {
        try {
            lock.lock();
            Thread.sleep(500);
            value = v;
            System.out.println(Thread.currentThread().getName() + ":" + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Runnable readRunnable = () -> {
            // read(reentrantLock);
            read(readLock);
        };
        Runnable writeRunnable = () -> {
            // write(reentrantLock, new Random().nextInt());
            write(writeLock, new Random().nextInt());
        };
        // 18个线程读
        for (int i = 0; i < 18; i++) {
            new Thread(readRunnable).start();
        }
        // 2个线程写
        for (int i = 0; i < 2; i++) {
            new Thread(writeRunnable).start();
        }
    }

}

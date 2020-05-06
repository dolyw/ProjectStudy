package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * AQS - AbstractQueuedSynchronizer
 * https://www.cnblogs.com/qlsem/p/11487783.html
 * https://blog.csdn.net/qq_36520235/article/details/81263037
 *
 * 在Lock中，用到了一个同步队列AQS，全称AbstractQueuedSynchronizer，
 * 它是一个同步工具也是Lock用来实现线程同步的核心组件
 *
 * 从使用层面来说，AQS的功能分为两种：独占和共享
 * 独占锁: 每次只能有一个线程持有锁，比如前面给大家演示的ReentrantLock就是以独占方式实现的互斥锁
 * 共享锁: 允许多个线程同时获取锁，并发访问共享资源，比如ReentrantReadWriteLock
 *
 * AQS队列内部维护的是一个FIFO的双向链表(CLH同步队列)，这种结构的特点是每个数据结构都有两个指针，
 * 分别指向直接的后继节点和直接前驱节点。所以双向链表可以从任意一个节点开始很方便的访问前驱和后继。
 * 每个Node其实是由线程封装，当线程争抢锁失败后会封装成Node加入到AQS队列中去
 * 当获取锁的线程释放锁以后，会从队列中唤醒一个阻塞的节点(线程)
 *
 * CLH同步队列是一个FIFO双向队列，AQS依赖它来完成同步状态的管理，当前线程如果获取同步状态失败时，
 * AQS则会将当前线程已经等待状态等信息构造成一个节点(Node)并将其加入到CLH同步队列，同时会阻塞当前线程，
 * 当同步状态释放时，会把首节点唤醒(公平锁)，使其再次尝试获取同步状态。
 * 在CLH同步队列中，一个节点表示一个线程，
 * 它保存着线程的引用(thread)、状态(status)、前驱节点(prev)、后继节点(next)
 *
 * 下面代码自行实现了一把锁
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 16:25
 */
public class T10_AQS {

    public static class CustomSync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }

    public static class CustomLock implements Lock {

        private CustomSync sync = new CustomSync();

        @Override
        public void lock() {
            sync.acquire(1);
        }

        @Override
        public void unlock() {
            sync.release(1);
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }

    public static int m = 0;

    public static Lock lock = new CustomLock();

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[100];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                try {
                    // lock.lock();
                    for (int j = 0; j < 100; j++) {
                        m++;
                    }
                } finally {
                    // lock.unlock();
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        System.out.println(m);
    }
}

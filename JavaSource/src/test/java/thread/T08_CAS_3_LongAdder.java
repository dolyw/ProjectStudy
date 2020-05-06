package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * CAS - 无锁优化(自旋) - 乐观锁
 *
 * AtomicLong，synchronized，LongAdder效率对比
 *
 * 各有各的快，线程太多的话，LongAdder快，synchronized慢
 *
 * AtomicLong底层CAS
 * synchronized底层锁升级，偏向锁 -> 轻量级锁 -> 重量级锁
 * LongAdder底层分段数组CAS，Striped64类
 *
 * LongAdder有一个根据当前并发状况动态改变的Cell数组，Cell对象里面有一个long类型的value用来存储值，
 * 开始没有并发争用的时候或者是cells数组正在初始化的时候，会使用CAS来将值累加到成员变量的base上，
 * 在并发争用的情况下，LongAdder会初始化cells数组，，在Cell数组中选定一个Cell加锁，
 * 数组有多少个cell，就允许同时有多少线程进行修改，最后将数组中每个Cell中的value相加，在加上base的值，为最终值
 * 可以看到获取值的时候调用sum方法，进行base + 数组遍历的值
 * cell数组还能根据当前线程争用情况进行扩容，初始长度为2，每次扩容会增长一倍，直到扩容到大于等于CPU数量就不再扩容
 *
 * LongAdder类与AtomicLong类的区别在于高并发时，将对单一变量的CAS操作分散为对数组cells中多个元素的CAS操作，
 * 取值时进行求和；而在并发较低时仅对base变量进行CAS操作，与AtomicLong类原理相同
 *
 * https://www.jianshu.com/p/ec045c38ef0c
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/17 14:42
 */
public class T08_CAS_3_LongAdder {

    private static AtomicLong count1 = new AtomicLong(0L);
    private static long count2 = 0L;
    private static LongAdder count3 = new LongAdder();

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 100000; k++) {
                    count1.incrementAndGet();
                }
            });
        }
        long start = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        long end = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("Atomic: " + count1.get() + " time " + (end - start));
        // -----------------------------------------------------------
        Object lock = new Object();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int k = 0; k < 100000; k++) {
                        synchronized (lock) {
                            count2++;
                        }
                    }
                }
            });
        }
        start = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        end = System.currentTimeMillis();
        System.out.println("Sync: " + count2 + " time " + (end - start));
        // ----------------------------------
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 100000; k++) {
                    count3.increment();
                }
            });
        }
        start = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        end = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(10);
        System.out.println("LongAdder: " + count1.longValue() + " time " + (end - start));

    }

}

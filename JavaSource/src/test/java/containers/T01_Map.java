package containers;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Hashtable - synchronized修饰的容器
 * HashMap - 线程不安全容器
 * Collections.synchronizedMap - synchronized修饰的容器
 * ConcurrentHashMap - 高并发容器
 * ConcurrentSkipListMap - 高并发且排序 - 跳表结构 - 用CAS实现树太复杂，所以用跳表
 * LinkedHashMap - 线程不安全容器
 * TreeMap - 线程不安全容器
 *
 * 这个代码测试1000000个元素put和get速度
 *
 * Hashtable - put时间355，元素1000000，get时间49662
 * HashMap - put时间188，元素810631，get时间2285，元素缺失
 * Collections.synchronizedMap - put时间401，元素1000000，get时间47967
 * ConcurrentHashMap - put时间229，元素1000000，get时间907
 * ConcurrentSkipListMap - put时间592，元素1000000，get时间24713
 *
 * ConcurrentHashMap和ConcurrentSkipListMap
 * https://blog.csdn.net/sunxianghuang/article/details/52221913
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 14:12
 */
public class T01_Map {

    public static final int COUNT = 1000000;
    public static final int THREAD_COUNT = 100;

    // public static Map<UUID, UUID> m = new Hashtable<>();
    // public static Map<UUID, UUID> m = new HashMap<>();
    // public static Map<UUID, UUID> m = Collections.synchronizedMap(new HashMap<UUID, UUID>());
    public static Map<UUID, UUID> m = new ConcurrentHashMap<>();
    // public static Map<UUID, UUID> m = new ConcurrentSkipListMap<>();

    public static UUID[] keys = new UUID[COUNT];
    public static UUID[] values = new UUID[COUNT];

    static {
        for (int i = 0; i < COUNT; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    public static class MyThread extends Thread {
        int start;
        int gap = COUNT / THREAD_COUNT;

        public MyThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < start + gap; i++) {
                m.put(keys[i], values[i]);
            }
        }
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread(i * (COUNT / THREAD_COUNT));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);

        System.out.println(m.size());

        //-----------------------------------

        start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000000; j++) {
                    m.get(keys[10]);
                }
            });
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}

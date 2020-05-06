package containers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList - 线程安全，写时复制容器
 * 多线程环境下，写时效率低，读时效率高，适合读多写少的环境
 *
 * 内部实现，添加元素add()时使用ReentrantLock锁，以当前数组复制出一个新数组，且长度+1，
 * 将元素加在新数组最后一位，再将引用指向新数组
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T02_List_3_CopyOnWriteArrayList {

    public static void runAndComputeTime(Thread[] ths) {
        long s1 = System.currentTimeMillis();
        Arrays.asList(ths).forEach(t -> t.start());
        Arrays.asList(ths).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
    }

    public static void main(String[] args) {
        List<String> lists = new CopyOnWriteArrayList<>();
        Random r = new Random();
        Thread[] ths = new Thread[100];

        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    lists.add("a" + r.nextInt(10000));
                }
            });
        }

        runAndComputeTime(ths);
        System.out.println(lists.size());
    }

}

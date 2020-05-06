package jvm.reference;

import java.lang.ref.SoftReference;

/**
 * 软引用
 * 空间不够才回收，用于缓存
 * 软引用是用来描述一些还有用但并非必须的对象
 * 对于软引用关联着的对象，在系统将要发生内存溢出异常之前，将会把这些对象列进回收范围进行第二次回收
 * 如果这次回收还没有足够的内存，才会抛出内存溢出异常
 *
 * 先设置VM options:-Xms20M -Xmx20M
 * 设置堆内存最大最小都是20M
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 17:58
 */
public class T02_SoftReference {

    public static void main(String[] args) {
        // 分配了20M
        SoftReference<byte[]> softReference = new SoftReference<>(new byte[1024 * 1024 * 10]);
        System.out.println(softReference.get());
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(softReference.get());
        // 再分配一个12M数组，heap将装不下，这时候系统会垃圾回收，先回收一次，如果不够，会把软引用干掉
        byte[] b = new byte[1024 * 1024 * 12];
        System.out.println(softReference.get());
    }

}

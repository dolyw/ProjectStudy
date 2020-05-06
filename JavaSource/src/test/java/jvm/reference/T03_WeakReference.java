package jvm.reference;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 * 只要调用了垃圾回收(System.gc())就回收，应用场景：只要强引用消失，则应该被回收，
 * 一般用在容器里，典型应用ThreadLock，看下WeakHashMap、AQSunlock源码(Tomcat缓存用的是弱应用)
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 19:09
 */
public class T03_WeakReference {

    public static void main(String[] args) {
        WeakReference<Custom> weakReference = new WeakReference<>(new Custom());

        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());

        ThreadLocal<Custom> threadLocal = new ThreadLocal<>();
        threadLocal.set(new Custom());
        // ThreadLocal用完必须remove()，不然存在内存泄露
        threadLocal.remove();

    }

}

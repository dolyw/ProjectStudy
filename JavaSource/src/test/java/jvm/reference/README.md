# 引用

> [https://www.cnblogs.com/Courage129/p/12734506.html](https://www.cnblogs.com/Courage129/p/12734506.html)

一个变量指向new对象，就是引用，在Java中有四种引用，分别是强软弱虚

* 强引用(T01_NormalReference)

只要有引用就不会被回收

* 软引用(T02_SoftReference)

空间不够才回收

* 弱引用(T03_WeakReference)

只要调用了垃圾回收(System.gc())就回收，应用场景：只要强引用消失，则应该被回收，一般用在容器里，典型应用ThreadLock，看下WeakHashMap、AQSunlock源码(Tomcat缓存用的是弱应用)

* 虚引用(T04_PhantomReference)

了解就行
package thread;

import java.util.concurrent.TimeUnit;

/**
 * 关键字 - volatile
 * https://blog.csdn.net/u012723673/article/details/80682208
 * https://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 *
 * 两大作用 1-保证线程可见性(使一个变量在多个线程间可见) 2-防止指令重排序
 *
 * 为什么需要保证线程可见性
 *
 * 共享变量存储在主内存(Main Memory)中，每个线程都有一个私有的本地内存（Local Memory），
 * 本地内存保存了被该线程使用到的主内存的副本拷贝，线程对变量的所有操作都必须在工作内存中进行，
 * 而不能直接读写主内存中的变量
 *
 * 对于普通的共享变量来讲，线程A将其修改为某个值发生在线程A的本地内存中，此时还未同步到主内存中去，
 * 而线程B已经缓存了该变量的旧值，所以就导致了共享变量值的不一致，
 * 解决这种共享变量在多线程模型中的不可见性问题，较粗暴的方式自然就是加锁，
 * 但是此处使用synchronized或者Lock这些方式太重量级了，比较合理的方式其实就是volatile
 *
 * 简单来说，使用volatile关键字，会让所有线程都会读到变量的修改值
 * 在下面的代码中，mark是存在于堆内存的volatileTest对象中
 * 当线程t开始运行的时候，会把mark值从内存中读到t线程的工作区，在运行过程中直接使用这个copy，并不会每次都去
 * 读取堆内存，这样，当主线程修改mark的值之后，t线程感知不到，所以不会停止运行
 * 使用volatile，将会强制所有线程都去堆内存中读取mark的值
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/16 10:20
 */
public class T07_volatile_1 {

    private static class VolatileTest {

        public /*volatile*/ Boolean mark = Boolean.TRUE;

        public void m() {
            while (mark) {

            }
        }
    }

    public static void main(String[] args) {
        VolatileTest volatileTest = new VolatileTest();
        // lambda表达式 new Thread(new Runnable( run() {volatileTest.m()}
        new Thread(volatileTest::m, "t").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        volatileTest.mark = Boolean.FALSE;
    }

}

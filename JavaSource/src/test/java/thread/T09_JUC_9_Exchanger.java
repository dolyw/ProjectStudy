package thread;

import java.util.concurrent.Exchanger;

/**
 * Exchanger - 用于两个工作线程之间交换数据的封装工具类
 * https://www.jianshu.com/p/990ae2ab1ae0
 * 简单说就是一个线程在完成一定的事务后想与另一个线程交换数据，
 * 则第一个先拿出数据的线程会一直等待第二个线程，直到第二个线程拿着数据到来时才能彼此交换对应数据
 *
 * 下面代码将两个线程里的字符串进行了交换
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 10:45
 */
public class T09_JUC_9_Exchanger {

    public static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(() -> {
            String s = "T1";
            System.out.println(Thread.currentThread().getName() + ":" + s);
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":" + s);
        }, "t1").start();

        new Thread(() -> {
            String s = "T2";
            System.out.println(Thread.currentThread().getName() + ":" + s);
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":" + s);
        }, "t2").start();
    }

}

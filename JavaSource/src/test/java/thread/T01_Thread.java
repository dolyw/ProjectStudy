package thread;

import java.util.concurrent.TimeUnit;

/**
 * 线程的创建方式一 - 继承Thread
 * 线程的创建方式二 - 实现Runnable
 * 线程的创建方式三 - JDK8直接创建
 *
 * 请你告诉我启动线程的三种方式 1：Thread 2: Runnable 3:Executors.newCachedThrad
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/14 10:20
 */
public class T01_Thread {

    private static class CreateThread1 extends Thread {

        @Override
        public void run() {
            // CreateThread1线程循环打印
            for (int i = 0; i < 50; i++) {
                try {
                    // 休眠1S，不然无法时时切换线程
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("CreateThread1");
            }
        }

    }

    private static class CreateThread2 implements Runnable {

        @Override
        public void run() {
            // CreateThread2线程循环打印
            for (int i = 50; i < 100; i++) {
                try {
                    // 休眠1S，不然无法时时切换线程
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("CreateThread2");
            }
        }

    }

    public static void main(String[] args) {
        // 线程的创建方式一 - 继承Thread
        CreateThread1 createThread1 = new CreateThread1();
        // 启动线程
        createThread1.start();
        // 线程的创建方式二 - 实现Runnable
        Thread createThread2 = new Thread(new CreateThread2());
        // 启动线程
        createThread2.start();
        // 线程的创建方式三 - JDK8直接创建
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    // 休眠1S，不然无法时时切换线程
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("CreateThread3");
            }
        }).start();
        // 主线程循环打印
        for (int i = 50; i < 100; i++) {
            try {
                // 休眠1S，不然无法时时切换线程
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Main");
        }
    }

    /**
     * JAVA中让当前线程睡眠一段时间的方式
     * 方法一：通过线程的sleep方法
     * Thread.sleep(1000);
     * Thread.currentThread().sleep(1000);
     * 上面两种没区别，参数1000是以毫秒为单位，即这语句可以让程序等待1秒
     * 方法二：TimeUnit类里的sleep方法
     * java.util.concurrent.TimeUnit;这个类里封装着
     * TimeUnit.DAYS.sleep(1); //天
     * TimeUnit.HOURS.sleep(1); //小时
     * TimeUnit.MINUTES.sleep(1); //分
     * TimeUnit.SECONDS.sleep(1); //秒
     * TimeUnit.MILLISECONDS.sleep(1000); //毫秒
     * TimeUnit.MICROSECONDS.sleep(1000); //微妙
     * TimeUnit.NANOSECONDS.sleep(1000); //纳秒
     * TimeUnit类提供的方法，底层调用的也是Thread类的sleep方法，只是在上层根据时间单位进行封装
     */
}

package thread;

/**
 * 了解一下就行 - 线程的休眠方式 - Thread.yield()
 * Thread.yield()与Thread.sleep(1000)或者TimeUnit.MILLISECONDS.sleep(1000)区别
 * 主要是前两者都是存在指定休眠时间，时间到了才能继续执行，而Thread.yield()只是让步
 * 就是当前线程让出当前时间片给其他线程执行，继续回到等待队列，也可能可能再次被选中执行
 *
 * yield()方法会通知线程调度器放弃对处理器的占用，但调度器可以忽视这个通知。
 * yield()方法主要是为了保障线程间调度的连续性，防止某个线程一直长时间占用cpu资源。
 * 但是他的使用应该基于详细的分析和测试。
 * 这个方法一般不推荐使用，它主要用于debug和测试程序，用来减少bug以及对于并发程序结构的设计
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/14 10:20
 */
public class T02_yield {

    private static class CreateThread implements Runnable {

        @Override
        public void run() {
            // CreateThread线程循环打印
            for (int i = 0; i < 50; i++) {
                System.out.println("CreateThread3");
            }
        }

    }

    public static void main(String[] args) {
        Thread thread = new Thread(new CreateThread());
        // 启动线程
        thread.start();
        // 写法
        Thread.yield();
        // 主线程循环打印
        for (int i = 0; i < 50; i++) {
            System.out.println("Main3");
        }
    }

    /**
     * JAVA中可以通过让当前线程睡眠一段时间的方式
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

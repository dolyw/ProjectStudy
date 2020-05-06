package thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 关键字 - volatile
 * https://blog.csdn.net/u012723673/article/details/80682208
 * https://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 *
 * 两大作用 1-保证线程可见性(使一个变量在多个线程间可见) 2-防止指令重排序
 *
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 *
 * 在下面的代码中，count是永远到不了10000的，因为count++不是原子性的
 * 但是给count++加上synchronized就是10000
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/16 10:20
 */
public class T07_volatile_3 {

    private static class VolatileTest {

        public volatile static Integer count = 0;

        public /*synchronized*/ void m() {
            for (int i = 0; i < 1000; i++) {
                /*synchronized (this) {*/
                    count++;
                /*}*/
            }
        }

        public static void main(String[] args) {
            VolatileTest volatileTest = new VolatileTest();
            List<Thread> threadList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                threadList.add(new Thread(volatileTest::m));
            }
            threadList.forEach(thread -> {
                thread.start();
            });
            threadList.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            // 输入永远到不了1000
            System.out.println(count);
        }

    }

}

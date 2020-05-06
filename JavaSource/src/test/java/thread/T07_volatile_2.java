package thread;

import java.util.concurrent.TimeUnit;

/**
 * 关键字 - volatile
 * https://blog.csdn.net/u012723673/article/details/80682208
 * https://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 *
 * 两大作用 1-保证线程可见性(使一个变量在多个线程间可见) 2-防止指令重排序
 *
 * volatile修饰引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 * 下面代码有两个例子
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/16 10:20
 */
public class T07_volatile_2 {

    private static class VolatileTest {

        public volatile static VolatileTest volatileTest = new VolatileTest();

        public /*volatile*/ Boolean mark = Boolean.TRUE;

        public void m() {
            while (mark) {

            }
        }

        public static void main(String[] args) {
            // VolatileTest volatileTest = new VolatileTest();
            new Thread(volatileTest::m, "t").start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            volatileTest.mark = Boolean.FALSE;
        }

    }

    // 分割线

    private static class Data {
        int a, b;

        public Data(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    volatile static Data data;

    public static void main(String[] args) {
        Thread writer = new Thread(()->{
            for (int i = 0; i < 10000; i++) {
                data = new Data(i, i);
            }
        });

        Thread reader = new Thread(()->{
            while (data == null) {}
            int x = data.a;
            int y = data.b;
            if(x != y) {
                System.out.printf("a = %s, b = %s%n", x, y);
            }
        });

        reader.start();
        writer.start();

        try {
            reader.join();
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end");
    }

}

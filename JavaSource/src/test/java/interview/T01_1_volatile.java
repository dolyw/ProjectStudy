package interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * (淘宝)实现一个容器，提供两个方法，add，size，
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 主要考的是线程的通信，用volatile修饰，加上同步容器
 * 但是这线程t2一直死循环允许，很浪费CPU，如果不用死循环，该怎么做呢，看后面的实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 14:15
 */
public class T01_1_volatile {

    public static class InterviewTest {

        // public List list = new ArrayList<>();
        // 不加volatile修饰，线程二无法实时获取size()数量
        // public volatile List list = new ArrayList<>();
        // 不是同步容器，线程不安全
        public volatile List list = Collections.synchronizedList(new ArrayList<>());

        public void add(Object o) {
            list.add(o);
        }

        public int size() {
            return list.size();
        }

        public static void main(String[] args) {
            InterviewTest interviewTest = new InterviewTest();
            // 起一个线程装10个元素
            new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    interviewTest.add(new Object());
                    System.out.println(i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t1").start();
            // 再起一个线程监听
            new Thread(() -> {
                while (true) {
                    if (interviewTest.size() == 8000) {
                        break;
                    }
                }
                System.out.println("结束");
            }, "t2").start();
        }

    }

}

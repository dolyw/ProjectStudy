package containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * ArrayList - 线程不安全容器 - 数组 - 读快，写删慢
 * LinkedList - 线程不安全容器 - 链表 - 读慢，写删快
 * Collections.synchronizedList
 *
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T02_List_2_ArrayList_LinkedList {

    // public static List<String> tickets = new ArrayList<>();
    // public static List<String> tickets = Collections.synchronizedList(new ArrayList<>());
    public static List<String> tickets = new LinkedList<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 读取完tickets.size() > 0后
                // 再最后一个元素，可能几个线程都进去了，第一个线程移除，后面的线程就报错了
                // 卖超，所以这两个步骤得加上锁
                // 推荐将synchronized写在while里
                while (true) {
                    synchronized (tickets) {
                        if (tickets.size() <= 0) {
                            break;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("销售了票--" + tickets.remove(0));
                    }
                }
            }).start();
        }
    }

}

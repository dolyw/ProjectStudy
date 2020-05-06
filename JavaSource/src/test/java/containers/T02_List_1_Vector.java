package containers;

import java.util.List;
import java.util.Vector;

/**
 * Vector - 早期线程安全容器
 * 内部方法全部加锁性能低下，不推荐使用
 *
 * 下面代码tickets.size()和tickets.remove()虽然是同步的，但是组合操作不是同步的
 * 所以操作A和B都是同步的，但A和B组成的复合操作也未必是同步的，仍然需要自己进行同步
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T02_List_1_Vector {

    public static List<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 1000; i++) {
            tickets.add("编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 虽然Vector内部是线程安全的，但是读取完tickets.size() > 0后
                // 再最后一个元素，可能几个线程都进去了，第一个线程移除，后面的线程就报错了
                // 卖超，所以这两个步骤得加上锁
                /*synchronized (tickets) {*/
                    while (tickets.size() > 0) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("销售了票--" + tickets.remove(0));
                    }
                /*}*/
            }).start();
        }
    }

}

package containers;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * LinkedTransferQueue - 与SynchronousQueue区别，可以支持多个线程对多个线程，容量不为0
 * 可以先保存下来，等其他线程进行获取
 *
 * transfer() - 没有返回值，一直等待其他人消费
 * tryTransfer() - 返回值boolean，尝试加入
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T04_TransferQueue_LinkedTransferQueue {

    public static void main(String[] args) throws InterruptedException {
        TransferQueue<String> strings = new LinkedTransferQueue<>();

        new Thread(() -> {
            try {
                System.out.println(strings.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 直接加进去
        // strings.put("aaa");
        // 一直等待
        // strings.transfer("bbb");
        // 尝试加入，失败false，成功true
        System.out.println(strings.tryTransfer("ccc"));
        // 尝试等待1S加入
        strings.tryTransfer("ddd", 1, TimeUnit.SECONDS);

        new Thread(() -> {
            try {
                System.out.println(strings.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}

package thread;

/**
 * 线程的方法 - join()
 * 调用某线程的某方法，将当前线程和该线程合并，等待该线程结束，再恢复当前线程的允许
 *
 * createThread1.join();
 * createThread2.join();
 * 主线程调用join()，线程1，2交替执行
 *
 * 如果完全按顺序执行，主线程调用线程2的join()，线程2内run方法第一行调用线程1的join()
 *
 * 当我们在join()方法中传入参数时，比如1000，那么主线程只会阻塞1000ms，然后就恢复了并行的执行状态
 * 简单说就是只会等待子线程执行1000ms
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/14 10:20
 */
public class T03_join {

    public static void main(String[] args) {
        // 创建线程一
        Thread createThread1 = new Thread(() -> {
            // CreateThread1线程循环打印
            for (int i = 0; i < 10; i++) {
                try {
                    // 休眠1S，不然无法时时切换线程
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("CreateThread1");
            }
        });
        // 创建线程二
        Thread createThread2 = new Thread(() -> {
            try {
                // 线程二里调用线程一的join()
                createThread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // CreateThread2线程循环打印
            for (int i = 20; i < 30; i++) {
                try {
                    // 休眠1S，不然无法时时切换线程
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("CreateThread2");
            }
        });
        // 启动线程
        createThread1.start();
        createThread2.start();
        /*try {
            // 主线程调用join()，线程1，2交替执行
            createThread1.join();
            createThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        try {
            // 如果完全按顺序执行，主线程调用线程2的join()，线程2内run方法第一行调用线程1的join()
            // createThread2.join();
            // 当我们在join()方法中传入参数时，比如1000，那么主线程只会阻塞1000ms，然后就恢复了并行的执行状态
            // createThread2.join(1000);
            // 参数区别第一个是毫秒，第二个是纳秒，等待时间就是毫秒+纳秒，时间更精确
            createThread2.join(1000, 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 主线程循环打印
        for (int i = 1; i < 10; i++) {
            try {
                // 休眠1S，不然无法时时切换线程
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Main");
        }
    }
}

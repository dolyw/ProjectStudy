package thread;

/**
 * 线程的状态 - getState() - State枚举
 *
 * NEW，新建状态，尚未启动的线程的线程状态
 *
 * RUNNABLE，可运行线程的线程状态，可运行程序中的线程，
 * 状态正在Java虚拟机中执行，但它可能正在等待来自操作系统的其他资源，例如处理器
 *
 * BLOCKED，等待监视器锁定时被阻止的线程的线程状态，
 * 处于阻塞状态的线程正在等待监视器锁定输入同步块/方法或调用后重新输入同步的块/方法 Object.wait()
 *
 * WAITING，等待线程的线程状态，由于调用以下方法 Object.wait()没有超时 Thread.join()没有超时 LockSupport.park()
 * 处于等待状态的线程正在等待另一个线程执行特定的动作
 *
 * TIMED_WAITING，具有指定等待时间的等待线程的线程状态，由于调用具有指定正等待时间的以下方法
 * Thread.sleep() Object.wait() Thread.join() LockSupport.parknos() LockSupport.parkUntil()
 *
 * TERMINATED，终止线程的线程状态，线程已完成执行
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/14 10:20
 */
public class T04_State {

    public static void main(String[] args) {
        // 创建线程
        Thread thread = new Thread(() -> {
            System.out.println("-----" + Thread.currentThread().getState());
            // 线程循环打印
            for (int i = 0; i < 5; i++) {
                try {
                    // 休眠1ms，不然无法时时切换线程
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("-----" + Thread.currentThread().getState());
                System.out.println("Thread");
            }
            System.out.println("-----" + Thread.currentThread().getState());
        }, "diy-thread");
        System.out.println("-----" + thread.getState().name());
        // 启动线程
        thread.start();
        System.out.println("-----" + thread.getState().name());
        // 主线程循环打印
        for (int i = 10; i < 15; i++) {
            try {
                // 休眠1ms，不然无法时时切换线程
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Main");
        }
        try {
            // 先执行完thread线程
            thread.join();
            System.out.println("-----" + thread.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----" + thread.getState());
    }
}

package thread;

/**
 * 线程死锁 - synchronized
 *
 * 两个线程互相持有对方需要锁，导致锁住，无法继续往下执行
 *
 * 两个对象锁Object是static的，所以下面New了两次DeadLockTest类，两个对象锁Object还是同一个
 * 如果两个对象锁Object不是static，那下面New了两次DeadLockTest类，就会有四个对象锁，不会出现死锁
 *
 * 嵌套死锁，A拿了B需要的锁，B拿了C需要的锁，C拿了D需要的锁，D...
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/15 10:20
 */
public class T06_DeadLock {

    private static class DeadLockTest implements Runnable {

        private static Object object1 = new Object();
        private static Object object2 = new Object();

        private String name = "default";

        public DeadLockTest(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            if ("WangMing".equals(name)) {
                synchronized (object1) {
                    try {
                        Thread.sleep(1100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (object2) {
                        System.out.println(name);
                    }
                }
            }
            if ("XiaoMing".equals(name)) {
                synchronized (object2) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (object1) {
                        System.out.println(name);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread deadLock1 = new Thread(new DeadLockTest("WangMing"));
        Thread deadLock2 = new Thread(new DeadLockTest("XiaoMing"));
        deadLock1.start();
        deadLock2.start();
    }
}

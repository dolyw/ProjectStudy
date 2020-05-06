package thread;

/**
 * 线程锁 - synchronized - 可重入 - 不能重入就会导致死锁
 *
 * 锁升级 - 偏向锁 -> 轻量级锁 -> 重量级锁
 *
 * 偏向锁没有实际加锁，记录最初的线程ID，每次判断是不是最初的那个线程，是的话直接放行
 * 不是就把当前线程升级轻量级锁，进行CAS获取，没获取到再进入自旋等待
 * 自旋10圈后，还没获取到锁就升级重量级锁
 *
 * 执行时间短（加锁代码），线程数少，用自旋锁(自旋占用CPU)
 * 执行时间长，线程数多，用系统锁
 *
 * synchronized锁细化，控制代码少，性能更好
 * synchronized锁粗化，一个方法里太多synchronized细化锁，还不如直接当前方法一个大锁即可
 *
 * 下面的代码
 * m1()方法用的object对象锁，这样需要去创建很多对象，直接使用this即可
 * 所以m1()和m2()是相等的
 *
 * 如果m1()和m2()方法是整个锁，可以直接写在方法上，参考m3()
 * 所以m1()和m2()和m3()是相等的
 *
 * m4()和m5()是相等的，m4是静态方法，不能使用this，只能使用类.class
 * 其实本身还是Object(this)，类.class本身在初始化时会有一个Object的对象
 *
 * m6()方法object对象锁不能变化，object改变了，锁就变了，这个必须注意
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/15 10:20
 */
public class T05_synchronized {

    private static class SynchronizedTest {

        private static Integer count = 10;
        private Object object = new Object();

        public void m1() {
            synchronized (object) {
                count--;
                System.out.println(Thread.currentThread().getName() + "，count = " + count);
            }
        }

        public void m2() {
            synchronized (this) {
                count--;
                System.out.println(Thread.currentThread().getName() + "，count = " + count);
            }
        }

        public synchronized void m3() {
            count--;
            System.out.println(Thread.currentThread().getName() + "，count = " + count);
        }

        public static void m4() {
            synchronized (SynchronizedTest.class) {
                count--;
                System.out.println(Thread.currentThread().getName() + "，count = " + count);
            }
        }

        public synchronized static void m5() {
            count--;
            System.out.println(Thread.currentThread().getName() + "，count = " + count);
        }

        public void m6() {
            synchronized (object) {
                count--;
                System.out.println(Thread.currentThread().getName() + "，count = " + count);
            }
            object = new Object();
        }

    }
}

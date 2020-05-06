package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;

/**
 * CAS - 无锁优化(自旋) - 乐观锁
 *
 * ABA问题
 * https://www.cnblogs.com/dream2true/archive/2019/04/23/10759763.html
 * 如果内存地址V初次读取的值是A，在CAS等待期间它的值曾经被改成了B
 * 后来又被改回为A，那CAS操作就会误认为它从来没有被改变过
 *
 * 举例，本来值是1，当前线程打算改为2，还没改的时候，前面两个线程，其中一个改为3，另一个又改回1，
 * 当前线程继续修改，发现是1，就感觉之前没人修改一样
 *
 * 对于简单对象没什么影响，像布尔值、整型值等
 * 引用对象可能会存在问题
 * 举例一，你和女朋友1分手，你又找了个女朋友2，再分手，再和女朋友1复合，结果女朋友2怀孕了
 * 举例二，你和你女友分手，女友又找了个男朋友后，和你复合，女朋友怀孕发现不是你的孩子
 * 所以引用对象可能会存在问题
 *
 * ABA问题以及解决
 * 使用带版本号的原子引用AtomicStampedRefence<V>，或者叫时间戳的原子引用，类似于乐观锁
 *
 * 下面的代码
 * 普通原子引用类在另一个线程完成ABA之后继续修改(把A改成了C)，带版本号原子引用有效的解决了这个问题
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/17 14:42
 */
public class T08_CAS_2_AtomicStamped {

    private static AtomicReference<String> atomicReference = new AtomicReference<>("A");
    private static AtomicStampedReference<String> stampReference = new AtomicStampedReference<>("A", 1);

    public static void main(String[] args) {
        new Thread(() -> {
            // 获取到版本号1
            int stamp = stampReference.getStamp();
            System.out.println("t1获取到的版本号：" + stamp);
            try {
                // 暂停1秒，确保t1，t2版本号相同
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // ABA
            atomicReference.compareAndSet("A", "B");
            atomicReference.compareAndSet("B", "A");
            // ABA
            stampReference.compareAndSet("A", "B", stamp, stamp + 1);
            stampReference.compareAndSet("B", "A", stamp + 1, stamp + 2);
            // 输出版本号
            System.out.println("t1线程ABA之后的版本号：" + stampReference.getStamp());
        }, "t1").start();

        new Thread(() -> {
            // 获取到版本号为1
            int stamp = stampReference.getStamp();
            System.out.println("t2获取到的版本号：" + stamp);
            try {
                // 暂停2秒，等待t1执行完成ABA，版本号为3
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 普通原子类直接修改成功为C
            System.out.print("普通原子类无法解决ABA问题");
            System.out.print("，操作结果: " + atomicReference.compareAndSet("A", "C"));
            System.out.println("，值结果: " + atomicReference.get());
            // 版本号的原子类无法修改成功，因为版本号已经变3了，当前版本号传的是1
            System.out.print("版本号的原子类解决ABA问题");
            System.out.print("，操作结果: " + stampReference.compareAndSet("A", "C", stamp, stamp + 1));
            System.out.println("，值结果: " + stampReference.getReference());
        }, "t2").start();
    }

}

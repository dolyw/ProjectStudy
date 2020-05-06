package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS - 无锁优化(自旋) - 乐观锁
 * 判断内存中某个地址的值是否为预期值，如果是就改变成新值，整个过程具有原子性
 * https://www.cnblogs.com/fengzheng/p/9018152.html
 * https://www.jianshu.com/p/db8dce09232d
 *
 * CAS操作包含三个操作数：内存位置、预期原值和新值。
 * 如果内存位置的值与预期原值相匹配，那么处理器会自动将该位置值更新为新值。否则，处理器不做任何操作。
 *
 * 举例，3个参数，当前值(实际值)value，预期值(旧值)expect，要修改的值(更新后的值)update
 * 先判断当前值value(1)是不是预期值expect(1)，是的话就当前值value改成要修改的值update(2)，操作结束
 * 如果当前值value(2)不是预期值expect(1)，说明当前值value(1)被其他线程改了变成value(2)，就继续自旋
 * 重新判断当前值value(2)是不是预期值expect(2)，是的话就改成要修改的值update(3)，如此直到成功
 *
 * 借用AtomicXXX类(JUC并发包下的原子类)来了解CAS，因为AtomicXXX类底层用的都是CAS实现的
 * JDK8查看incrementAndGet方法内部实现，是使用Unsafe(操作内存)下的CompareAndSwap(比较并交换)(native)方法
 *
 * AtomicXXX类本身方法都是原子性的，但不能保证多个方法连续调用是原子性的
 * CAS适合冲突较少的情况，如果太多线程在同时自旋，那么长时间循环会导致CPU开销很大
 *
 * 下面的代码是之前 T07_volatile_3 的改版，改用AtomicInteger类，可以不再使用volatile和synchronized
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/17 14:42
 */
public class T08_CAS_1_Atomic {

    private static class AtomicIntegerTest {

        public AtomicInteger count = new AtomicInteger(0);

        public void m() {
            for (int i = 0; i < 1000; i++) {
                count.incrementAndGet();
            }
        }

        public static void main(String[] args) {
            AtomicIntegerTest atomicIntegerTest = new AtomicIntegerTest();
            List<Thread> threadList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                threadList.add(new Thread(atomicIntegerTest::m));
            }
            threadList.forEach(thread -> {
                thread.start();
            });
            threadList.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            // 输入永远到不了1000
            System.out.println(atomicIntegerTest.count);
        }

    }

}

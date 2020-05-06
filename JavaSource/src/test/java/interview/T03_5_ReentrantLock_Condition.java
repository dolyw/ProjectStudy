package interview;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 要求用两个线程顺序交替打印1-26，A-Z，例如A1B2C3....Z26
 *
 * 主要考的是线程的通信，这里我们使用ReentrantLock和Condition
 *
 * Condition可以用来区分任务队列，比如使用producerCondition进行await()，这个线程就进入producerCondition队列，
 * 下次producerCondition进行signalAll()就会将线程都唤醒
 *
 * Condition中的await()方法相当于Object的wait()方法，Condition中的signal()方法相当于Object的notify()方法，
 * Condition中的signalAll()相当于Object的notifyAll()方法
 *
 * 不同的是，Object中的这些方法是和同步锁捆绑使用的，而Condition是需要与互斥锁/共享锁捆绑使用的
 *
 * Condition它更强大的地方在于能够更加精细的控制多线程的休眠与唤醒
 * 对于同一个锁，我们可以创建多个Condition，在不同的情况下使用不同的Condition
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 14:15
 */
public class T03_5_ReentrantLock_Condition {

    public static class InterviewTest {

        public static char[] aI = "123456789".toCharArray();

        public static char[] aC = "ABCDEFGHI".toCharArray();

        public static void main(String[] args) {
            ReentrantLock lock = new ReentrantLock();
            Condition conditionT1 = lock.newCondition();
            Condition conditionT2 = lock.newCondition();

            new Thread(() -> {
                lock.lock();
                try {
                    for (char c : aC) {
                        System.out.print(c);
                        conditionT1.signal();
                        conditionT2.await();
                    }
                    conditionT1.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }, "t1").start();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(() -> {
                try {
                    lock.lock();
                    for (char i : aI) {
                        System.out.print(i);
                        conditionT2.signal();
                        conditionT1.await();
                    }
                    conditionT2.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }, "t2").start();
        }

    }

}

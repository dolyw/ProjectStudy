package interview;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法，能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 为什么用while，不用if
 * 因为进入wait()等待着，如果醒了继续往下执行应该再判断是不是strings.size() == MAX_SIZE队列已经满了
 * 所以用if的话会出现超过最大容量继续加元素的情况
 * 同理，get方法里，如果用if，会出现大小为0的时候还去removeFirst()就异常了
 *
 * 使用ReentrantLock和Condition
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
 * @date 2020/4/24 9:38
 */
public class T02_2_ReentrantLock_Condition {

    public static class MyContainer<T> {

        private final LinkedList<T> strings = new LinkedList<>();

        // 最大容量
        private static final int MAX_SIZE = 5;

        // count
        private int count = 0;

        // ReentrantLock
        public Lock lock = new ReentrantLock();

        // Condition
        public Condition producerCondition = lock.newCondition();
        public Condition consumerCondition = lock.newCondition();

        public void put(T s) {
            try {
                lock.lock();
                // 为什么用while，不用if
                while (strings.size() == MAX_SIZE) {
                    // this.wait();
                    producerCondition.await();
                }
                strings.add(s);
                ++count;
                System.out.println(Thread.currentThread().getName());
                // this.notifyAll();
                // consumerCondition.signal();
                consumerCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public T get() {
            T s = null;
            try {
                lock.lock();
                // 为什么用while，不用if
                while (strings.size() == 0) {
                    // this.wait();
                    consumerCondition.await();
                }
                s = strings.removeFirst();
                count--;
                System.out.println(Thread.currentThread().getName());
                // this.notifyAll();
                // producerCondition.signal();
                producerCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return s;
        }

        public int getCount() {
            return count;
        }

    }

    public static void main(String[] args) {
        MyContainer<Object> myContainer = new MyContainer();
        // 4个生产者，1个生产者生产6个元素
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                for (int j = 0; j < 6; j++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myContainer.put(new Object());
                }
            }, "p" + i).start();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 8个消费者，1个消费者消费3个元素
        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myContainer.get();
                }
            }, "c" + i).start();
        }
    }

}

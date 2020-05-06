package thread;

/**
 * ThreadLocal - 线程副本 - 线程隔离特性 - 使用Person类
 * https://www.jianshu.com/p/98b68c97df9b
 * https://www.jianshu.com/p/1a5d288bdaee
 *
 * 下面代码使用ThreadLocal，两个线程读取的不是同一个Person类，使用完必须remove()，
 * 不然会存在内存泄露
 *
 * jvm.reference了解四种引用，ThreadLocal为何内存泄漏
 *
 * 由于ThreadLocalMap的Key是弱引用，而Value是强引用。这就导致了一个问题，ThreadLocal在没有外部对象强引用时，
 * 发生GC时弱引用Key会被回收，而Value不会回收，如果创建ThreadLocal的线程一直持续运行，
 * 那么这个Entry对象中的Value就有可能一直得不到回收，发生内存泄露
 *
 * 如何避免内存泄露
 * 既然Key是弱引用，那么我们要做的事，就是在使用完ThreadLocal后必须再调用remove()方法，
 * 将Entry节点和Map的引用关系移除，这样整个Entry对象在GC Roots分析后就变成不可达了，下次GC的时候就可以被回收
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/22 17:34
 */
public class T11_ThreadLocal_2 {

    public static class Person {
        public String name = "WangMing";
    }

    // public /*volatile*/ static Person person = new Person();
    public static ThreadLocal<Person> personThreadLocal = new ThreadLocal<Person>() {
        /**
         * 每个线程没值返回的初始化值
         * @return
         */
        @Override
        protected Person initialValue() {
            return new Person();
        }
    };

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(500);
                System.out.println(personThreadLocal.get().name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 最后必须remove()清空值，不然存在内存泄露
                personThreadLocal.remove();
            }
        }).start();

        new Thread(() -> {
            try {
                Person person = new Person();
                person.name = "WangXiaoMing";
                personThreadLocal.set(person);
                System.out.println(personThreadLocal.get().name);
            } finally {
                // 最后必须remove()清空值，不然存在内存泄露
                personThreadLocal.remove();
            }
        }).start();
    }

}

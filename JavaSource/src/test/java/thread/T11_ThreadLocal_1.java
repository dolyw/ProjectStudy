package thread;

/**
 * ThreadLocal - 线程副本 - 线程隔离特性 - 未使用ThreadLocal
 * https://www.jianshu.com/p/6fc3bba12f38
 *
 * 下面代码没有使用ThreadLocal，两个线程读取的都是同一个Person类，线程安全问题
 * Person类volatile加不加都一样，因为是引用类型，除非Person类引用变化了，volatile对于属性是不生效的
 * 可是存在某些需求，使变量在每个线程中都有独立拷贝，不会出现一个线程读取变量时而被另一个线程修改的现象
 *
 * 当某些数据是以线程为作用域并且不同线程具有不同的数据副本的时候，就可以考虑采用ThreadLocal
 *
 * 常见的就是数据库连接、Session管理等
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/22 17:34
 */
public class T11_ThreadLocal_1 {

    public static class Person {
        public String name = "WangMing";
    }

    public /*volatile*/ static Person person = new Person();

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(person.name);
            try {
                Thread.sleep(555);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(person.name);
        }).start();

        new Thread(() -> {
            System.out.println(person.name);
            person.name = "WangXiaoMing";
            System.out.println(person.name);
        }).start();
    }

}

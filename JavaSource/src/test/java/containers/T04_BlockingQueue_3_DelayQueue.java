package containers;

import java.util.concurrent.*;

/**
 * DelayQueue - 按时间排序出队列
 *
 * 任务调度 - 定时任务 - 延时队列
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T04_BlockingQueue_3_DelayQueue {

    public static BlockingQueue<MyTask> tasks = new DelayQueue<>();

    public static class MyTask implements Delayed {
        public String name;
        public long runningTime;

        MyTask(String name, long rt) {
            this.name = name;
            this.runningTime = rt;
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public String toString() {
            return name + " " + runningTime;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        MyTask t1 = new MyTask("t1", now + 10000);
        MyTask t2 = new MyTask("t2", now + 20000);
        MyTask t3 = new MyTask("t3", now + 15000);
        MyTask t4 = new MyTask("t4", now + 25000);
        MyTask t5 = new MyTask("t5", now + 5000);

        tasks.put(t1);
        tasks.put(t2);
        tasks.put(t3);
        tasks.put(t4);
        tasks.put(t5);

        System.out.println(tasks);

        /*for (int i = 0; i < 5; i++) {
            System.out.println(tasks.take());
        }

        tasks.put(t1);
        tasks.put(t2);
        tasks.put(t3);
        tasks.put(t4);
        tasks.put(t5);*/

        MyTask t = null;
        while (tasks.size() > 0) {
            t = tasks.take();
            System.out.println(t.runningTime + ", " + System.currentTimeMillis());
        }
    }

}

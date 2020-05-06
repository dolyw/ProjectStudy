package thread;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

/**
 * ForkJoinPool - JDK1.8新增newWorkStealingPool的底层实现
 * https://blog.csdn.net/tjbsl/article/details/98480843
 *
 * 分解汇总的任务
 * 用很少的线程可以执行很多的任务(子任务)，ThreadPoolExecutor做不到先执行子任务
 * CPU密集型
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 17:17
 */
public class T12_ThreadPool_7_ForkJoinPool {

    public static class TestForkJoinPool {
        static int[] nums = new int[1000000];
        static final int MAX_NUM = 50000;
        static Random r = new Random();

        static {
            for (int i = 0; i < nums.length; i++) {
                nums[i] = r.nextInt(100);
            }
            // stream api
            System.out.println("---" + Arrays.stream(nums).sum());
        }

        public static class AddTask extends RecursiveAction {
            int start, end;

            AddTask(int s, int e) {
                start = s;
                end = e;
            }

            @Override
            protected void compute() {
                if (end - start <= MAX_NUM) {
                    long sum = 0L;
                    for (int i = start; i < end; i++) sum += nums[i];
                    System.out.println("from:" + start + " to:" + end + " = " + sum);
                } else {
                    int middle = start + (end - start) / 2;

                    AddTask subTask1 = new AddTask(start, middle);
                    AddTask subTask2 = new AddTask(middle, end);
                    subTask1.fork();
                    subTask2.fork();
                }
            }
        }


        public static class AddTaskRet extends RecursiveTask<Long> {

            private static final long serialVersionUID = 1L;
            int start, end;

            AddTaskRet(int s, int e) {
                start = s;
                end = e;
            }

            @Override
            protected Long compute() {
                if (end - start <= MAX_NUM) {
                    long sum = 0L;
                    for (int i = start; i < end; i++) sum += nums[i];
                    return sum;
                }
                int middle = start + (end - start) / 2;
                AddTaskRet subTask1 = new AddTaskRet(start, middle);
                AddTaskRet subTask2 = new AddTaskRet(middle, end);
                subTask1.fork();
                subTask2.fork();
                return subTask1.join() + subTask2.join();
            }
        }

        public static void main(String[] args) throws IOException {
            /*ForkJoinPool fjp = new ForkJoinPool();
            AddTask task = new AddTask(0, nums.length);
            fjp.execute(task);*/
            TestForkJoinPool temp = new TestForkJoinPool();
            ForkJoinPool fjp = new ForkJoinPool();
            AddTaskRet task = new AddTaskRet(0, nums.length);
            fjp.execute(task);
            long result = task.join();
            System.out.println(result);
            // System.in.read();
        }
    }

}

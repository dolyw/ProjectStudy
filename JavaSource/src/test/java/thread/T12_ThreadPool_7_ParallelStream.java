package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ParallelStream - 并行流 - 底层用的ForkJoinPool
 *
 * 把任务都拆成子任务，不用保证线程同步安全可以使用加快效率
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/27 18:36
 */
public class T12_ThreadPool_7_ParallelStream {

    public static class ParallelStream {

        /**
         * 是不是质数
         *
         * @param num
         * @return boolean
         * @throws
         * @author wliduo[i@dolyw.com]
         * @date 2020/4/27 18:42
         */
        public static boolean isPrime(int num) {
            for (int i = 2; i <= num / 2; i++) {
                if (num % i == 0) return false;
            }
            return true;
        }

        public static void main(String[] args) {
            List<Integer> integerList = new ArrayList<>();
            Random r = new Random();
            for (int i = 0; i < 10000; i++) {
                integerList.add(1000000 + r.nextInt(1000000));
            }

            // System.out.println(integerList);

            long start = System.currentTimeMillis();
            // Stream流
            integerList.forEach(v -> isPrime(v));
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            // ParallelStream并行流，把任务都拆成子任务
            // 不用保证线程同步安全可以使用加快效率
            start = System.currentTimeMillis();
            integerList.parallelStream().forEach(ParallelStream::isPrime);
            end = System.currentTimeMillis();
            System.out.println(end - start);
        }
    }

}

package thread;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * CompletableFuture - 线程异步结果汇总操作
 *
 * 下面代码，priceOfTM()，priceOfTB()，priceOfJD()三个方法代码去不同地方查询价格
 * delay()睡眠500ms
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/26 17:17
 */
public class T12_ThreadPool_5_CompletableFuture {

    private static double priceOfTM() {
        delay();
        return 1.00;
    }

    private static double priceOfTB() {
        delay();
        return 2.00;
    }

    private static double priceOfJD() {
        delay();
        return 3.00;
    }

    /*private static double priceOfAmazon() {
        delay();
        throw new RuntimeException("product not exist!");
    }*/

    private static void delay() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long start, end;
        // 按顺序执行
        start = System.currentTimeMillis();
        priceOfTM();
        priceOfTB();
        priceOfJD();
        end = System.currentTimeMillis();
        System.out.println("use serial method call! " + (end - start));
        // 线程异步执行
        start = System.currentTimeMillis();
        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(() -> priceOfTM());
        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(() -> priceOfTB());
        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(() -> priceOfJD());
        // join等三个任务执行完成
        CompletableFuture.allOf(futureTM, futureTB, futureJD).join();
        // 做一些操作
        /*CompletableFuture.supplyAsync(() -> priceOfTM())
                .thenApply(String::valueOf)
                .thenApply(str -> "price " + str)
                .thenAccept(System.out::println);*/
        end = System.currentTimeMillis();
        System.out.println("use completable future! " + (end - start));
        try {
            // Main程序不停止
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

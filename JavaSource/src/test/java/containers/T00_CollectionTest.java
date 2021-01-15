package containers;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Collection
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/22 18:30
 */
public class T00_CollectionTest {

    public static void main(String[] args) {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Arrays.stream(array).map(i -> i + 1).forEach(i -> System.out.print(i + " "));
        System.out.println();

        System.out.println("-----");

        Collection<Integer> collection = new ArrayList();
        collection.add(1);
        collection.add(2);
        collection.add(3);
        collection.stream().forEach(System.out::println);

        System.out.println("-----");

        Queue<Integer> linkedList = new LinkedList<>();
        linkedList.addAll(collection);
        System.out.println(linkedList);

        System.out.println("-----");

        // 阻塞队列定义大小为2，不能add，只能添加
        Queue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(0);
        queue.add(1);
        // 超过2个异常了
        // queue.add(2);
        // queue.add(3);
        System.out.println(queue);

    }

}

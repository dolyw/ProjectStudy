package containers;

import java.util.PriorityQueue;

/**
 * PriorityQueue
 *
 * 会自动排序
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/23 15:35
 */
public class T03_Queue_2_PriorityQueue {

    public static void main(String[] args) {
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();

        priorityQueue.add("c");
        priorityQueue.add("e");
        priorityQueue.add("a");
        priorityQueue.add("d");
        priorityQueue.add("z");

        for (int i = 0; i < 5; i++) {
            System.out.println(priorityQueue.poll());
        }
    }

}

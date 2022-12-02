package com.algorithm.sort;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 堆排序 - PriorityQueue实现
 *
 * PriorityQueue默认是一个小顶堆
 * 可以通过传入自定义的Comparator函数来实现大顶堆
 *
 * https://blog.csdn.net/chuyangxunfeng/article/details/124648524
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/12/01 10:03
 */
public class HeapSort2 {

    /**
     * 堆排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {10, 8, 12, 9, 5, 3, 7, 1, 6, 4, 2, 11};

        System.out.println("---------- heapSort ----------");

        print(array);

        // PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
        // 创建大顶堆，默认小顶堆
        PriorityQueue<Integer> heap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        for (int i = 0; i < array.length; i++) {
            heap.offer(array[i]);
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = heap.poll();
        }

        print(array);
    }

    /**
     * 打印数组
     *
     * @param array
     */
    private static void print(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}

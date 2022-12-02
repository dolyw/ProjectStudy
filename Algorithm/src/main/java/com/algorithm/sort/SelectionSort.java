package com.algorithm.sort;

import java.util.Arrays;

/**
 * 选择排序
 *
 * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置
 * 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾
 * 重复第二步，直到所有元素均排序完毕
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/11/22 9:52
 */
public class SelectionSort {

    /**
     * 选择排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {5, 3, 2, 4, 9, 8, 7, 1, 6};

        System.out.println("---------- selectionSort1 ----------");
        selectionSort1(Arrays.copyOf(array, array.length));

        System.out.println("---------- selectionSort2 ----------");
        selectionSort2(Arrays.copyOf(array, array.length));

        System.out.println("---------- selectionSort3 ----------");
        selectionSort3(Arrays.copyOf(array, array.length));

        System.out.println("---------- selectionSort4 ----------");
        selectionSort4(Arrays.copyOf(array, array.length));
    }

    /**
     * 先写出一轮的选择排序处理
     *
     * 可以看到最小的数字交换到第一个了
     *
     * @param array
     */
    private static void selectionSort1(int[] array) {
        print(array);

        // 记录最小下标位置
        int minIndex = 0;

        for (int j = 0; j < array.length; j++) {
            if (array[j] < array[minIndex]) {
                minIndex = j;
            }
        }

        // 将最小下标值进行交换
        int temp = array[0];
        array[0] = array[minIndex];
        array[minIndex] = temp;

        print(array);
    }

    /**
     * 基于一轮的选择排序处理，再增加一个外循环
     * 将每个位置的数字都进行一轮选择交换
     * 这样一个最基础的选择排序就完成了
     *
     * @param array
     */
    private static void selectionSort2(int[] array) {
        print(array);

        // count记录循环次数
        int count = 0;

        for (int i = 0; i < array.length; i++) {
            // 记录最小下标位置
            int minIndex = i;

            for (int j = i; j < array.length; j++) {
                count++;
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            // 将最小下标值进行交换
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }

        System.out.println("count: " + count);

        print(array);
    }

    /**
     * 优化版本，边界处理优化
     *
     * 应该从j=i+1开始，i为0时，如果j=i
     * 那就是array[0]和array[0]比较了一次
     *
     * 外循环也应该i < array.length - 1，也是一样
     * 不进行-1的话结尾array[8]和array[8]多比较了一次
     *
     * @param array
     */
    private static void selectionSort3(int[] array) {
        print(array);

        // count记录循环次数
        int count = 0;

        for (int i = 0; i < array.length - 1; i++) {
            // 记录最小下标位置
            int minIndex = i;

            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
                count++;
            }

            // 将最小下标值进行交换
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }

        System.out.println("count: " + count);

        print(array);
    }

    /**
     * 优化版本v2，交换可以优化，不是每次都需要交换
     *
     * 如果下标是一样的，说明当前数就是最小的
     * 没必要自己和自己交换了
     *
     * @param array
     */
    private static void selectionSort4(int[] array) {
        print(array);

        // count记录循环次数
        int count = 0;

        for (int i = 0; i < array.length - 1; i++) {
            // 记录最小下标位置
            int minIndex = i;

            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
                count++;
            }

            // 最小下标位置和i不相等说明找到新的最小数才进行交换
            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }

        System.out.println("count: " + count);

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

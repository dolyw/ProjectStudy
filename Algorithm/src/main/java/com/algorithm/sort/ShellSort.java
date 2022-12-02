package com.algorithm.sort;

import java.util.Arrays;

/**
 * 希尔排序，又称递减（缩小）增量排序
 * 它与插入排序的不同之处在于，它通过比较相距一定间隔的元素来进行
 * 各趟比较所用的距离随着算法的进行而减小，直到只比较相邻元素的最后一趟排序为止
 *
 * 增量计算方式有两种
 * 1. 常规折半计算 array.length / 2，用序列 {n/2,(n/2)/2...1} 来表示
 * 2. knuth 序列（克努特序列），h = 3*h + 1，(1,4,13,40,121...)
 *
 * https://www.runoob.com/data-structures/shell-sort.html
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/11/25 15:04
 */
public class ShellSort {

    /**
     * 希尔排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {10, 8, 12, 9, 5, 3, 7, 1, 6, 4, 2, 11};

        System.out.println("---------- shellSort1 ----------");
        shellSort1(Arrays.copyOf(array, array.length));

        System.out.println("---------- shellSort2 ----------");
        shellSort2(Arrays.copyOf(array, array.length));

        System.out.println("---------- shellSort3 ----------");
        shellSort3(Arrays.copyOf(array, array.length));
    }

    /**
     * 先写出第一轮排序，把插入复制过过来
     * 增加为12个数，按间隔，gap设定为4
     * 要改为 while (j - gap >= 0 大于等0，不然第一位数无法被排序
     *
     * 可以看到每4个间隔比较的数都进行了插入排序
     *
     * @param array
     */
    private static void shellSort1(int[] array) {
        print(array);

        int gap = 4;

        // 从下标为 gap 开始，因为要和上一个 gap 比较，所以从 gap 开始
        for (int i = gap; i < array.length; i++) {
            int current = array[i];

            // 从后往前的开始比较，没有小于的数据后结束循环进行插入
            int j = i;
            while (j - gap >= 0 && (current < array[j - gap])) {
                array[j] = array[j - gap];
                j = j - gap;
            }

            // j和i不一致说明找到了比其小的数，进行插入
            if (j != i) {
                array[j] = current;
            }
        }

        print(array);
    }

    /**
     * 基础版本，增加外循环，在此我们选择常规折半计算 array.length / 2
     * 递减增量以 gap = gap / 2 的方式，用序列 {n/2,(n/2)/2...1} 来表示
     *
     * while (j - gap >= 0 也相等于 j - gap > -1 也相等于 j > gap - 1
     *
     * @param array
     */
    private static void shellSort2(int[] array) {
        print(array);

        for (int gap = array.length / 2; gap > 0; gap = gap / 2) {

            // 从下标为 gap 开始，因为要和上一个 gap 比较，所以从 gap 开始
            for (int i = gap; i < array.length; i++) {
                int current = array[i];

                // 从后往前的开始比较，没有小于的数据后结束循环进行插入
                int j = i;
                // while (j - gap >= 0 && (current < array[j - gap])) {
                while (j > gap - 1 && (current < array[j - gap])) {
                    array[j] = array[j - gap];
                    j = j - gap;
                }

                // j和i不一致说明找到了比其小的数，进行插入
                if (j != i) {
                    array[j] = current;
                }
            }

            System.out.println(gap);
            print(array);
        }

        // print(array);
    }

    /**
     * 优化版本，使用 knuth 序列计算出对应的递减增量 gap，h = 3*h + 1
     *
     * @param array
     */
    private static void shellSort3(int[] array) {
        print(array);

        // 计算增量
        int gap = 1;
        while (gap < array.length / 3) {
            gap = 3 * gap + 1;
        }

        while (gap > 0) {

            // 从下标为 gap 开始，因为要和上一个 gap 比较，所以从 gap 开始
            for (int i = gap; i < array.length; i++) {
                int current = array[i];

                // 从后往前的开始比较，没有小于的数据后结束循环进行插入
                int j = i;
                // while (j - gap >= 0 && (current < array[j - gap])) {
                while (j > gap - 1 && (current < array[j - gap])) {
                    array[j] = array[j - gap];
                    j = j - gap;
                }

                // j和i不一致说明找到了比其小的数，进行插入
                if (j != i) {
                    array[j] = current;
                }
            }

            System.out.println(gap);
            print(array);

            // 重新计算增量
            gap = (gap - 1) / 3;
        }

        // print(array);
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

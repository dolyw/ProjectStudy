package com.algorithm.sort;

import java.util.Arrays;

/**
 * 单轴双路快速排序，交换法(Hoare)
 *
 * 轴（基准）选择方式有：首尾元素，随机选取，三数取中法
 * 这里采用首元素
 *
 * 三数取中法，取首中尾三个数中间的那个数
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/11/28 9:52
 */
public class QuickSort {

    /**
     * 快速排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {5, 3, 7, 9, 4, 8, 2, 6, 1};

        System.out.println("---------- quickSort1 ----------");
        quickSort1(Arrays.copyOf(array, array.length));

        System.out.println("---------- quickSort2 ----------");
        quickSort2(Arrays.copyOf(array, array.length));

        System.out.println("---------- quickSort3 ----------");
        quickSort3(Arrays.copyOf(array, array.length));

    }

    /**
     * 先写出一轮的快速排序处理
     * 设定 pivot = 0 的轴进行快排
     *
     * 从左边开始，找到一个小于pivot的数
     * 从右边开始，找到有个大于pivot的数
     * 然后进行交换，输出可以看到，数据进行了两组交换
     *
     * @param array
     */
    private static void quickSort1(int[] array) {
        print(array);

        // 设定 pivot = 0 的轴进行快排
        int pivot = 0;
        int left = pivot + 1, right = array.length - 1;

        while (left <= right) {
            while (left <= right && array[left] <= array[pivot]) {
                left++;
            }
            while (left <= right && array[right] >= array[pivot]) {
                right--;
            }
            if (left <= right) {
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;

                // System.out.println("left " + left + " right " + right);
                print(array);
            }
        }

        // System.out.println("left " + left + " right " + right);
        // 交换轴，右下标是比轴小的数据，所以轴和右下标交换
        int temp = array[pivot];
        array[pivot] = array[right];
        array[right] = temp;

        print(array);
    }

    /**
     * 抽取公共方法
     *
     * @param array
     */
    private static void quickSort2(int[] array) {
        print(array);

        int partitionIndex = partition(array, 0, array.length - 1);
        // System.out.println("partitionIndex " + partitionIndex);

        // print(array);
    }

    /**
     * 抽取方法
     *
     * @param array
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] array, int left, int right) {
        int pivot = left;
        int i = pivot + 1, j = right;

        while (i <= j) {
            while (i <= j && array[i] <= array[pivot]) {
                i++;
            }
            while (i <= j && array[j] >= array[pivot]) {
                j--;
            }
            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;

                // System.out.println("i " + i + " j " + j);
                print(array);
            }
        }

        // System.out.println("s i " + i + " j " + j);

        // 交换轴，右下标是比轴小的数据，所以轴和右下标交换
        int temp = array[left];
        array[left] = array[j];
        array[j] = temp;

        print(array);

        return j;
    }

    /**
     * 递归处理，基础版本
     *
     * @param array
     */
    private static void quickSort3(int[] array) {
        print(array);

        sort(array, 0, array.length - 1);

        // print(array);
    }

    /**
     * 递归方法
     *
     * @param array
     * @param left
     * @param right
     */
    private static void sort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int partitionIndex = partition(array, left, right);
        // System.out.println("left " + left + " right " + right + " partitionIndex " + partitionIndex);
        sort(array, left, partitionIndex - 1);
        sort(array, partitionIndex + 1, right);

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

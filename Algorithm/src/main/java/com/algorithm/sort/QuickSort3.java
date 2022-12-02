package com.algorithm.sort;

import java.util.Arrays;

/**
 * 单轴双路快速排序，挖坑法，pivot 这里采用首元素
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/11/28 9:52
 */
public class QuickSort3 {

    /**
     * 快速排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {5, 3, 7, 9, 4, 8, 2, 6, 1};

        System.out.println("---------- quickSort3 ----------");
        quickSort3(Arrays.copyOf(array, array.length));
    }

    /**
     * 抽取方法
     * 单轴双路快速排序，挖坑法，pivot 这里采用首元素
     *
     * @param array
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] array, int left, int right) {
        int pivot = array[left];
        int i = left, j = right;

        while (i < j) {
            // 从右向左找出比pivot小的数据
            while (i <= j && array[j] > pivot) {
                j--;
            }
            // 找到后立即放入左边坑中，当前位置变为新的"坑"
            if (i < j) {
                array[i] = array[j];
                i++;
            }
            // 从左向右找出比pivot大的数据
            while (i <= j && array[i] <= pivot) {
                i++;
            }
            // 找到后立即放入右边坑中，当前位置变为新的"坑"
            if (i < j) {
                array[j] = array[i];
                j--;
            }
        }

        // System.out.println("s i " + i + " j " + j);

        // 将最开始存储的分界值放入当前的"坑"中
        array[j] = pivot;

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

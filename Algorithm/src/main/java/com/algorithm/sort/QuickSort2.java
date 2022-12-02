package com.algorithm.sort;

import java.util.Arrays;

/**
 * 单轴单路快速排序，前后指针法，pivot 这里采用首元素
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/11/28 9:52
 */
public class QuickSort2 {

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
     * 单轴单路快速排序，前后指针法，pivot 这里采用首元素
     *
     * @param array
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] array, int left, int right) {
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (array[i] < array[pivot]) {
                int temp = array[i];
                array[i] = array[index];
                array[index] = temp;
                index++;
            }
        }
        int temp = array[pivot];
        array[pivot] = array[index - 1];
        array[index - 1] = temp;
        print(array);
        return index - 1;
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

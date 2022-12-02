package com.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序 - 原生实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/11/28 9:52
 */
public class MergeSort {

    /**
     * 归并排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9, 2, 4, 6, 8};

        System.out.println("---------- mergeSort1 ----------");
        mergeSort1(Arrays.copyOf(array, array.length));

        int[] array2 = {3, 4, 5, 2, 7, 8, 9, 1, 6};

        System.out.println("---------- mergeSort2 ----------");
        mergeSort2(Arrays.copyOf(array2, array2.length));

        int[] array3 = {5, 3, 2, 9, 4, 8, 7, 6, 1};

        System.out.println("---------- mergeSort3 ----------");
        mergeSort3(Arrays.copyOf(array3, array3.length));
    }

    /**
     * 先写出一轮的归并处理
     *
     * @param array
     */
    private static void mergeSort1(int[] array) {
        print(array);

        // 计算中间位置
        int midIndex = array.length / 2;
        // 临时数组
        int[] tempArray = new int[array.length];

        int i = 0, j = midIndex + 1, k = 0;
        // j为中间位置+1，i和j对半进行比较，哪个小就放哪个
        while (i <= midIndex && j < array.length) {
            // <=，这样保证前面的和后面相等数，前面先下去，保证稳定性
            if (array[i] <= array[j]) {
                tempArray[k] = array[i];
                i++;
                k++;
            } else {
                tempArray[k] = array[j];
                j++;
                k++;
            }
        }
        // 对比完，多余剩下的数
        while (i <= midIndex) {
            tempArray[k] = array[i];
            i++;
            k++;
        }
        while (j < array.length) {
            tempArray[k] = array[j];
            j++;
            k++;
        }

        print(tempArray);
    }

    /**
     * 抽取方法进行归并验证
     *
     * @param array
     */
    private static void mergeSort2(int[] array) {

        print(array);

        // rightIndex 传 length - 1
        merge(array, 0, 2, 4);

        print(array);

        merge(array, 5, 6, 8);

        print(array);
    }

    /**
     * 抽取合并方法
     *
     * @param array
     * @param leftIndex
     * @param midIndex
     * @param rightIndex
     */
    private static void merge(int[] array, int leftIndex, int midIndex, int rightIndex) {
        // 数组长度需要 + 1，因为 rightIndex 传的 length - 1
        int[] tempArray = new int[rightIndex + 1 - leftIndex];

        int i = leftIndex, j = midIndex + 1, k = 0;
        // j为中间位置+1，i和j对半进行比较，哪个小就放哪个
        while (i <= midIndex && j < rightIndex + 1) {
            // <=，这样保证前面的和后面相等数，前面先下去，保证稳定性
            if (array[i] <= array[j]) {
                tempArray[k++] = array[i++];
            } else {
                tempArray[k++] = array[j++];
            }
        }
        // 对比完，多余剩下的数
        while (i <= midIndex) {
            tempArray[k++] = array[i++];
        }
        while (j < rightIndex + 1) {
            tempArray[k++] = array[j++];
        }

        // 将结果赋值给原数组
        for (int m = 0; m < tempArray.length; m++) {
            array[leftIndex + m] = tempArray[m];
        }

        print(tempArray);

    }

    /**
     * 基础版本，进行递归处理
     *
     * @param array
     */
    private static void mergeSort3(int[] array) {

        print(array);

        sort(array, 0, array.length - 1);

        print(array);

        System.out.println("---------- analysis ----------");
        System.out.println("[5 3 2 9 4] [8 7 6 1]");
        System.out.println("[5 3 2] [9 4]");
        System.out.println("[5 3] [2]");
        System.out.println("[5] [3]");
        System.out.println("[9] [4]");
        System.out.println("[8 7] [6 1]");
        System.out.println("[8] [7]");
        System.out.println("[6] [1]");
    }

    /**
     * 递归方法
     *
     * @param array
     * @param leftIndex
     * @param rightIndex
     */
    private static void sort(int[] array, int leftIndex, int rightIndex) {
        // System.out.println(leftIndex + "--" + rightIndex);
        if (leftIndex == rightIndex) {
            return;
        }

        // 两种都是一样的结果，计算两个下标的中间下标
        // int minIndex = (leftIndex + rightIndex) / 2;
        // 右下标减去左下标的差值的部分除以 2 得出差值的中间值，加左下标的部分得到总的中间下标
        int minIndex = leftIndex + (rightIndex - leftIndex) / 2;

        sort(array, leftIndex, minIndex);
        sort(array, minIndex + 1, rightIndex);

        merge(array, leftIndex, minIndex, rightIndex);
        // 优化版本，已经有序的两个数不进行重复归并
        /*if (array[minIndex] > (array[minIndex + 1])) {
            merge(array, leftIndex, minIndex, rightIndex);
        }*/
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

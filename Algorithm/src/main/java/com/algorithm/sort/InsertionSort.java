package com.algorithm.sort;

import java.util.Arrays;

/**
 * 插入排序
 *
 * 将第一待排序序列第一个元素看做一个有序序列，把第二个元素到最后一个元素当成是未排序序列
 * 从头到尾依次扫描未排序序列，将扫描到的每个元素插入有序序列的适当位置
 * 如果待插入的元素与有序序列中的某个元素相等，则将待插入元素插入到相等元素的后面
 *
 * 和打扑克牌类似
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/11/22 9:52
 */
public class InsertionSort {

    /**
     * 插入排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {5, 3, 2, 4, 9, 8, 7, 1, 6};

        System.out.println("---------- insertionSort1 ----------");
        insertionSort1(Arrays.copyOf(array, array.length));

        System.out.println("---------- insertionSort2 ----------");
        insertionSort2(Arrays.copyOf(array, array.length));

        System.out.println("---------- insertionSort3 ----------");
        insertionSort3(Arrays.copyOf(array, array.length));
    }

    /**
     * 先写出一轮的插入插入处理
     *
     * 设定是从第2个数往前插入
     *
     * @param array
     */
    private static void insertionSort1(int[] array) {
        print(array);

        int current = array[2];

        // 从第2个开始比较，没有小于的数据后结束循环进行插入
        int j = 2;
        while (j > 0 && (current < array[j - 1])) {
            array[j] = array[j - 1];
            j--;
        }

        // j不等于2，说明找到了比其小的数，才进行插入
        if (j != 2) {
            array[j] = current;
        }

        // 使用交换的方式
        /*for (int j = 2; j > 0; j--) {
            if (array[j] < array[j - 1]) {
                int temp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = temp;
            }
        }*/

        print(array);
    }

    /**
     * 基于一轮的插入排序处理，再增加一个外循环
     *
     * 这样一个最基础的插入排序就完成了
     *
     * @param array
     */
    private static void insertionSort2(int[] array) {
        print(array);

        for (int i = 0; i < array.length; i++) {
            int current = array[i];

            // 从后往前的开始比较，没有小于的数据后结束循环进行插入
            int j = i;
            while (j > 0 && (current < array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }

            // j和i不一致说明找到了比其小的数，才进行插入
            if (j != i) {
                array[j] = current;
            }
        }

        // 使用交换的方式
        /*for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }*/

        print(array);
    }

    /**
     * 优化版本，边界处理
     * i可以直接从1开始，0的只有一个元素无需处理
     *
     * 交换的方式的话，优化i < array.length - 1
     *
     * @param array
     */
    private static void insertionSort3(int[] array) {
        print(array);

        // 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素无需处理
        for (int i = 1; i < array.length; i++) {
            int current = array[i];

            // 从后往前的开始比较，没有小于的数据后结束循环进行插入
            int j = i;
            while (j > 0 && (current < array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }

            // j和i不一致说明找到了比其小的数，才进行插入
            if (j != i) {
                array[j] = current;
            }
        }

        // 使用交换的方式
        /*for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }*/

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

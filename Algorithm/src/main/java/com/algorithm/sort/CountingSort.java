package com.algorithm.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 计数排序
 * 适用于存储数的数据范围很小的情况下
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/12/01 10:03
 */
public class CountingSort {

    /**
     * 计数排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {5, 12, 10, 9, 6, 9, 7, 9, 9, 8, 5, 7, 8, 5, 6, 6, 6, 5, 7, 9, 8, 5, 15};

        System.out.println("---------- countingSort1 ----------");
        countingSort1(Arrays.copyOf(array, array.length));

        int[] array2 = {5, 12, 10, 9, 6, 9, 7, 9, -9, -8, 5, 7, 8, 5, 6, 6, -6, -5, 7, 9, 8, 5, 15};

        System.out.println("---------- countingSort2 ----------");
        countingSort2(Arrays.copyOf(array2, array2.length));

        System.out.println("---------- countingSort3 ----------");
        countingSort3(Arrays.copyOf(array, array.length));

        System.out.println("---------- countingSort4 ----------");
        countingSort4(Arrays.copyOf(array2, array2.length));
    }

    /**
     * 计数排序基础版本，不稳定，不支持负数
     * 使用最大值计算需要几个bucket
     *
     * 只用最大值计算这样会导致bucket过大浪费
     *
     * @param array
     */
    private static void countingSort1(int[] array) {
        print(array);

        // 找到最大值计算需要几个bucket
        int maxValue = 0;
        for (int value : array) {
            if (maxValue < value) {
                maxValue = value;
            }
        }
        // 创建bucket
        int bucketLen = maxValue + 1;
        int[] bucket = new int[bucketLen];
        // 遍历数组，将值放入bucket中
        for (int value : array) {
            bucket[value]++;
        }
        // 输出bucket的值
        for (int i = 0, pos = 0; i < bucketLen; i++) {
            while (bucket[i] > 0) {
                array[pos] = i;
                pos++;
                bucket[i]--;
            }
        }

        System.out.println("bucketLen " + bucketLen);
        print(array);
    }

    /**
     * 计数排序优化版本，不稳定，支持负数
     * 使用最大值和最小值计算极值差+1个bucket
     *
     * 可以看到bucket数量变小了
     * 存数时减去最小值，支持负数
     *
     * @param array
     */
    private static void countingSort2(int[] array) {
        print(array);

        // 找到最大值和最小值计算需要几个bucket
        int maxValue = array[0], minValue = array[0];
        for (int value : array) {
            if (maxValue < value) {
                maxValue = value;
            } else if (minValue > value) {
                minValue = value;
            }
        }
        // 创建bucket
        int bucketLen = maxValue - minValue + 1;
        int[] bucket = new int[bucketLen];
        // 遍历数组，将值放入bucket中
        for (int value : array) {
            // 优化减小了bucket的大小
            int index = value - minValue;
            bucket[index]++;
            // bucket[value - minValue]++;
        }
        // print(bucket);
        // 输出bucket的值，按存取的方式取出元素
        for (int i = 0, pos = 0; i < bucket.length; i++) {
            // 根据bucket数组中存的次数去取排列好的元素
            while (bucket[i] > 0) {
                array[pos] = minValue + i;
                pos++;
                bucket[i]--;
            }
        }

        System.out.println("bucketLen " + bucketLen);
        print(array);
    }

    /**
     * 计数排序优化版本v2，利用累加数组做到算法稳定，不支持负数
     * 使用最大值计算需要几个bucket
     *
     * @param array
     */
    private static void countingSort3(int[] array) {
        print(array);

        // 找到最大值计算需要几个bucket
        int maxValue = 0;
        for (int value : array) {
            if (maxValue < value) {
                maxValue = value;
            }
        }
        // 创建bucket
        int bucketLen = maxValue + 1;
        int[] bucket = new int[bucketLen];
        // 遍历数组，将值放入bucket中
        for (int value : array) {
            bucket[value]++;
        }

        print(bucket);

        int[] tempArray = new int[array.length];
        // 利用累加数组计算出元素下标
        for (int i = 1; i < bucket.length; i++) {
            bucket[i] = bucket[i] + bucket[i - 1];
        }

        print(bucket);

        // 利用累加数组的方式取出元素，从最后一位开始
        for (int i = array.length - 1; i >= 0; i--) {
            // 取出原数组每一位的值
            int value = array[i];
            // 将其-1后得到的就是它的在bucket中的下标
            bucket[value]--;
            // 根据bucket加下标得到该元素最终在有序数组中的下标
            int pos = bucket[value];
            // 赋值到新数组对应的下标位置
            tempArray[pos] = array[i];
        }

        System.out.println("bucketLen " + bucketLen);
        print(tempArray);
    }

    /**
     * 计数排序优化版本v3，利用累加数组做到算法稳定，支持负数
     * 使用最大值和最小值计算极值差+1个bucket
     * 存数时减去最小值，支持负数
     *
     * @param array
     */
    private static void countingSort4(int[] array) {
        print(array);

        // 找到最大值和最小值计算需要几个bucket
        int maxValue = array[0], minValue = array[0];
        for (int value : array) {
            if (maxValue < value) {
                maxValue = value;
            } else if (minValue > value) {
                minValue = value;
            }
        }
        // 创建bucket
        int bucketLen = maxValue - minValue + 1;
        int[] bucket = new int[bucketLen];
        // 遍历数组，将值放入bucket中
        for (int value : array) {
            // 优化减小了bucket的大小
            int index = value - minValue;
            bucket[index]++;
            // bucket[value - minValue]++;
        }

        // print(bucket);

        int[] tempArray = new int[array.length];
        // 利用累加数组计算出元素下标
        for (int i = 1; i < bucket.length; i++) {
            bucket[i] = bucket[i] + bucket[i - 1];
        }

        // print(bucket);

        // 利用累加数组的方式取出元素，从最后一位开始
        for (int i = array.length - 1; i >= 0; i--) {
            // 取出原数组每一位的值
            int value = array[i] - minValue;
            // 将其-1后得到的就是它的在bucket中的下标
            bucket[value]--;
            // 根据bucket加下标得到该元素最终在有序数组中的下标
            int pos = bucket[value];
            // 赋值到新数组对应的下标位置
            tempArray[pos] = array[i];
        }

        System.out.println("bucketLen " + bucketLen);
        // print(array);
        print(tempArray);
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

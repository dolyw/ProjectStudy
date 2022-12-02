package com.algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 桶排序
 * 适用于存储数的数据范围很小的情况下
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/12/01 10:03
 */
public class BucketSort {

    /**
     * 桶排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {5, 12, 10, 9, 6, 9, 7, 9, -9, -8, 5, 7, 8, 5, 6, 6, -6, -5, 7, 9, 8, 5, 15};

        System.out.println("---------- bucketSort1 ----------");
        bucketSort1(Arrays.copyOf(array, array.length));

        System.out.println("---------- bucketSort2 ----------");
        bucketSort2(Arrays.copyOf(array, array.length));
    }

    /**
     * 桶排序基础版本，指定bucketSize为5
     * 存数时减去最小值，支持负数
     *
     * @param array
     */
    private static void bucketSort1(int[] array) {
        print(array);

        int bucketSize = 5;
        int minValue = array[0];
        int maxValue = array[0];
        for (int value : array) {
            if (value < minValue) {
                minValue = value;
            } else if (value > maxValue) {
                maxValue = value;
            }
        }

        int bucketCount = (int) Math.floor((maxValue - minValue) / bucketSize) + 1;
        int[][] buckets = new int[bucketCount][0];

        // 利用映射函数将数据分配到各个桶中
        for (int i = 0; i < array.length; i++) {
            int index = (int) Math.floor((array[i] - minValue) / bucketSize);
            buckets[index] = arrAppend(buckets[index], array[i]);
        }

        int pos = 0;
        for (int[] bucket : buckets) {
            if (bucket.length <= 0) {
                continue;
            }
            // 对每个桶进行排序，这里使用了插入排序
            bucket = insertSort(bucket);
            for (int value : bucket) {
                array[pos] = value;
                pos++;
            }
        }

        print(array);
    }

    /**
     * 自动扩容，并保存数据
     *
     * @param array
     * @param value
     */
    private static int[] arrAppend(int[] array, int value) {
        array = Arrays.copyOf(array, array.length + 1);
        array[array.length - 1] = value;
        return array;
    }

    /**
     * 插入排序
     *
     * @param array
     * @return
     */
    private static int[] insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int current = array[i];
            int j = i;
            while (j > 0 && (current < array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }
            if (j != i) {
                array[j] = current;
            }
        }
        return array;
    }

    /**
     * 桶排序使用ArrayList实现，指定bucketSize为5
     * ArrayList支持负数存取
     *
     * @param array
     */
    private static void bucketSort2(int[] array) {
        print(array);

        int bucketSize = 5;
        List[] buckets = new ArrayList[bucketSize];
        // 初始化
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<Integer>();
        }
        // 将待排序序列放入对应桶中
        for (int i = 0; i < array.length; i++) {
            // 计算对应的桶号
            int index = array[i] / 10;
            buckets[index].add(array[i]);
        }
        // 排序输出
        for (int k = 0, pos = 0; k < buckets.length; k++) {
            // 每个桶内进行排序
            buckets[k].sort(null);
            for (int j = 0; j < buckets[k].size(); j++) {
                array[pos] = (int) buckets[k].get(j);
                pos++;
            }
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

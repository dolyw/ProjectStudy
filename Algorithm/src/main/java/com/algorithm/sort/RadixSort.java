package com.algorithm.sort;

import java.util.Arrays;

/**
 * 基数排序
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/12/01 10:03
 */
public class RadixSort {

    /**
     * 基数排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {5, 122, 102, 95, 63, 70, 88, 73, 88, 81, 555, 666, 729, 19, 28, 45, 1115};

        System.out.println("---------- radixSort1 ----------");
        radixSort1(Arrays.copyOf(array, array.length));

        int[] array2 = {5, 122, 102, 95, 63, -7, 88, 73, 88, 81, -555, -666, 729, -19, 28, 45, 1115};

        System.out.println("---------- radixSort2 ----------");
        radixSort2(Arrays.copyOf(array2, array2.length));

        System.out.println("---------- radixSort3 ----------");
        radixSort3(Arrays.copyOf(array2, array2.length));
    }

    /**
     * 基数排序基础版本，不支持负数
     *
     * @param array
     */
    private static void radixSort1(int[] array) {
        print(array);

        int[] tempArray = new int[array.length];
        int[] counter = new int[10];

        // 使用最大值计算位数
        int maxValue = array[0];
        for (int value : array) {
            if (maxValue < value) {
                maxValue = value;
            }
        }
        // 获取位数
        int maxDigit = getNumLength(maxValue);
        for (int i = 0; i < maxDigit; i++) {
            int division = (int) Math.pow(10, i);
            // System.out.println(division);

            for (int j = 0; j < array.length; j++) {
                int num = array[j] / division % 10;
                counter[num]++;
            }

            // 累加数组保证稳定
            for (int m = 1; m < counter.length; m++) {
                counter[m] = counter[m] + counter[m - 1];
            }

            // 利用累加数组的方式取出元素，从最后一位开始
            for (int n = array.length - 1; n >= 0; n--) {
                // 取出所在counter下标
                int num = array[n] / division % 10;
                // 将其-1后得到的就是它的在counter中的下标
                counter[num]--;
                // 根据counter加下标得到该元素最终在有序数组中的下标
                int pos = counter[num];
                // 赋值到新数组对应的下标位置
                tempArray[pos] = array[n];
            }

            // 将tempArray复制到array
            // System.arraycopy(tempArray, 0, array, 0, array.length);
            array = Arrays.copyOf(tempArray, tempArray.length);
            // 将counter数组值全部填充置为0，重新开始下一轮
            Arrays.fill(counter, 0);

            print(array);
        }

        // print(array);
    }

    /**
     * 获取数字位数
     *
     * @param num
     * @return
     */
    private static int getNumLength(long num) {
        if (num == 0) {
            return 1;
        }
        int length = 0;
        for (long temp = num; temp != 0; temp /= 10) {
            length++;
        }
        return length;
    }

    /**
     * 基数排序优化版本，支持负数
     *
     * 考虑负数的情况，扩展一倍队列数
     * 其中[0-9]对应负数，[10-19]对应正数(bucket + 10)
     *
     * @param array
     */
    private static void radixSort2(int[] array) {
        print(array);

        int[] tempArray = new int[array.length];
        // 考虑负数的情况，扩展一倍队列数，其中[0-9]对应负数，[10-19]对应正数(bucket + 10)
        int[] counter = new int[20];

        // 使用最大值计算位数
        int maxValue = array[0];
        for (int value : array) {
            if (maxValue < value) {
                maxValue = value;
            }
        }
        // 获取位数
        int maxDigit = getNumLength(maxValue);
        for (int i = 0; i < maxDigit; i++) {
            int division = (int) Math.pow(10, i);
            // System.out.println(division);

            for (int j = 0; j < array.length; j++) {
                int num = array[j] / division % 10;
                // 值大于等于0，存放在10-19的正数区域
                // 其中 [0-9]对应负数，[10-19]对应正数 (bucket + 10)
                num = num + 10;
                counter[num]++;
            }

            // 累加数组保证稳定
            for (int m = 1; m < counter.length; m++) {
                counter[m] = counter[m] + counter[m - 1];
            }

            // 利用累加数组的方式取出元素，从最后一位开始
            for (int n = array.length - 1; n >= 0; n--) {
                // 取出所在counter下标
                int num = array[n] / division % 10;
                // 值大于等于0，存放在10-19的正数区域
                // 其中 [0-9]对应负数，[10-19]对应正数 (bucket + 10)
                num = num + 10;
                // 将其-1后得到的就是它的在counter中的下标
                counter[num]--;
                // 根据counter加下标得到该元素最终在有序数组中的下标
                int pos = counter[num];
                // 赋值到新数组对应的下标位置
                tempArray[pos] = array[n];
            }

            // 将tempArray复制到array
            // System.arraycopy(tempArray, 0, array, 0, array.length);
            array = Arrays.copyOf(tempArray, tempArray.length);
            // 将counter数组值全部填充置为0，重新开始下一轮
            Arrays.fill(counter, 0);

            print(array);
        }

        // print(array);
    }

    /**
     * 基数排序优化版本v2，使用二维数组实现，支持负数
     *
     * @param array
     */
    private static void radixSort3(int[] array) {
        print(array);

        // 使用最大值计算位数
        int maxValue = array[0];
        for (int value : array) {
            if (maxValue < value) {
                maxValue = value;
            }
        }
        // 获取位数
        int maxDigit = getNumLength(maxValue);
        int mod = 10;
        int dev = 1;

        for (int i = 0; i < maxDigit; i++, dev = dev * 10, mod = mod * 10) {
            // 考虑负数的情况，扩展一倍队列数，其中[0-9]对应负数，[10-19]对应正数(bucket + 10)
            int[][] counter = new int[mod * 2][0];

            // 放入桶
            for (int j = 0; j < array.length; j++) {
                int bucket = ((array[j] % mod) / dev) + mod;
                counter[bucket] = arrayAppend(counter[bucket], array[j]);
            }

            // 稳定的，使用二维数组存放了值
            int pos = 0;
            for (int[] bucket : counter) {
                for (int value : bucket) {
                    array[pos++] = value;
                }
            }

            print(array);
        }

        // print(array);
    }

    /**
     * 自动扩容，并保存数据
     *
     * @param arr
     * @param value
     */
    private static int[] arrayAppend(int[] arr, int value) {
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = value;
        return arr;
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

package com.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * 比较相邻的元素。如果第一个比第二个大（小），就交换他们两个
 * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对
 * 这步做完后，最后的元素会是最大（小）的数
 * 针对所有的元素重复以上的步骤，除了最后一个
 *
 * 时间复杂度
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/11/22 9:52
 */
public class BubbleSort {

    /**
     * 冒泡排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {5, 3, 2, 4, 9, 8, 7, 1, 6};

        System.out.println("---------- bubbleSort1 ----------");
        bubbleSort1(Arrays.copyOf(array, array.length));

        System.out.println("---------- bubbleSort2 ----------");
        bubbleSort2(Arrays.copyOf(array, array.length));

        System.out.println("---------- bubbleSort3 ----------");
        bubbleSort3(Arrays.copyOf(array, array.length));

        System.out.println("---------- bubbleSort4 ----------");
        int[] array4 = {4, 1, 2, 3, 9, 8, 7, 5, 6};
        bubbleSort4(Arrays.copyOf(array4, array.length));
    }

    /**
     * 先写出一轮的冒泡排序处理，冒泡一样的交换位置到最后一位
     *
     * 大于就交换位置是从小到大，每次大于后面的值才交换，最后一位是最大值
     * 小于就交换位置是从大到小，每次小于后面的值才交换，最后一位是最小值
     *
     * array.length - 1，不然会超出边界，因为 array[j + 1]
     *
     * 当然也可以是从j = 1开始，j和j - 1进行比较，这样就不用array.length - 1了
     * 大于和小于也需要调换，大于会变成从大到小，小于会变成从小到大
     * 当然这种逆向的理解起来比较复杂一点，第二块代码是逆向的写法
     *
     * @param array
     */
    private static void bubbleSort1(int[] array) {
        print(array);

        for (int j = 0; j < array.length - 1; j++) {
            if (array[j] > array[j + 1]) {
                int temp = array[j];
                array[j] = array[j + 1];
                array[j + 1] = temp;
            }
        }

        /*for (int j = 1; j < array.length; j++) {
            if (array[j] < array[j - 1]) {
                int temp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = temp;
            }
        }*/

        print(array);
    }

    /**
     * 基于一轮的冒泡排序处理，再增加一个外循环
     * 将每个位置的数字都进行一轮冒泡交换
     * 这样一个最基础的冒泡排序就完成了
     *
     * 给定一个count记录循环次数
     *
     * @param array
     */
    private static void bubbleSort2(int[] array) {
        print(array);

        // count记录循环次数
        int count = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                count++;
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        System.out.println("count: " + count);

        print(array);
    }

    /**
     * 优化版本，内循环减去i就行了
     * 每次冒泡后到i位置的数字都已经排序好了
     * 所以就没必要重复排
     *
     * 可以看到count次数少了一半
     *
     * @param array
     */
    private static void bubbleSort3(int[] array) {
        print(array);

        // count记录循环次数
        int count = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
                count++;
            }
        }

        // 还有一种外循环i反过来循环的，意思差不多，就是逆向的
        /*for (int i = array.length - 1; i > 0 ; i--) {
            for (int j = 0; j < i; j++) {
                count++;
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }*/
        System.out.println("count: " + count);

        print(array);
    }

    /**
     * 优化版本v2，假定数组某些数已经有序
     * 在每轮交换处理可以定一个标记，如果这轮比较都没有交换位置的话
     * 说明数据已经有序，可以直接结束
     *
     * 给定一个count记录循环次数
     * 换一个存在某些数有序的数组，可以看到count次数又会变少
     *
     * @param array
     */
    private static void bubbleSort4(int[] array) {
        print(array);

        // count记录循环次数
        int count = 0;

        for (int i = 0; i < array.length; i++) {
            // 设定一个标记
            boolean mark = Boolean.TRUE;

            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    mark = Boolean.FALSE;
                }
                count++;
            }

            // 若为true，则表示此次循环没有进行交换
            if (mark) {
                // 也就是待排序列已经有序，排序已经完成，直接结束
                break;
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

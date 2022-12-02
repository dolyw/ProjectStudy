package com.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序 - 截取数组方式
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/11/28 9:52
 */
public class MergeSort2 {

    /**
     * 归并排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 2, 4, 6, 8, 9};

        System.out.println("---------- mergeSort1 ----------");
        mergeSort1(Arrays.copyOf(array, array.length));

        int[] array2 = {5, 3, 2, 9, 4, 8, 7, 6, 1};

        System.out.println("---------- mergeSort2 ----------");
        mergeSort2(Arrays.copyOf(array2, array2.length));
    }

    /**
     * 截取数组方式
     * 先写出一轮的归并处理
     *
     * @param array
     */
    private static void mergeSort1(int[] array) {
        print(array);

        // 计算中间位置
        int midIndex = array.length / 2;
        // 临时数组
        int[] leftArray = Arrays.copyOfRange(array, 0, midIndex);
        int[] rightArray = Arrays.copyOfRange(array, midIndex, array.length);

        int[] tempArray = new int[leftArray.length + rightArray.length];

        int i = 0;
        while (leftArray.length > 0 && rightArray.length > 0) {
            if (leftArray[0] <= rightArray[0]) {
                tempArray[i++] = leftArray[0];
                leftArray = Arrays.copyOfRange(leftArray, 1, leftArray.length);
            } else {
                tempArray[i++] = rightArray[0];
                rightArray = Arrays.copyOfRange(rightArray, 1, rightArray.length);
            }
        }

        while (leftArray.length > 0) {
            tempArray[i++] = leftArray[0];
            leftArray = Arrays.copyOfRange(leftArray, 1, leftArray.length);
        }

        while (rightArray.length > 0) {
            tempArray[i++] = rightArray[0];
            rightArray = Arrays.copyOfRange(rightArray, 1, rightArray.length);
        }

        print(tempArray);
    }

    /**
     * 截取数组方式基础版本，归并排序
     *
     * @param array
     */
    private static void mergeSort2(int[] array) {
        print(array);

        array = sort(array);

        print(array);
    }

    /**
     * 截取数组方式递归方法
     *
     * @param tempArray
     * @return
     */
    private static int[] sort(int[] tempArray) {
        // print(tempArray);
        if (tempArray.length < 2) {
            return tempArray;
        }

        // 计算中间位置
        int midIndex = tempArray.length / 2;

        // System.out.println(midIndex);

        // 计算左右两个数组
        int[] leftArray = Arrays.copyOfRange(tempArray, 0, midIndex);
        int[] rightArray = Arrays.copyOfRange(tempArray, midIndex, tempArray.length);

        return merge(sort(leftArray), sort(rightArray));
    }

    /**
     * 截取数组方式归并方法
     *
     * @param leftArray
     * @param rightArray
     * @return
     */
    private static int[] merge(int[] leftArray, int[] rightArray) {
        int[] tempArray = new int[leftArray.length + rightArray.length];

        int i = 0;
        while (leftArray.length > 0 && rightArray.length > 0) {
            if (leftArray[0] <= rightArray[0]) {
                tempArray[i++] = leftArray[0];
                leftArray = Arrays.copyOfRange(leftArray, 1, leftArray.length);
            } else {
                tempArray[i++] = rightArray[0];
                rightArray = Arrays.copyOfRange(rightArray, 1, rightArray.length);
            }
        }

        while (leftArray.length > 0) {
            tempArray[i++] = leftArray[0];
            leftArray = Arrays.copyOfRange(leftArray, 1, leftArray.length);
        }

        while (rightArray.length > 0) {
            tempArray[i++] = rightArray[0];
            rightArray = Arrays.copyOfRange(rightArray, 1, rightArray.length);
        }

        print(tempArray);
        return tempArray;
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

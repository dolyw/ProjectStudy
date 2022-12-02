package com.algorithm.sort;

/**
 * 堆排序 - 数组实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/12/01 10:03
 */
public class HeapSort {

    /**
     * 堆排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] array = {10, 8, 12, 9, 5, 3, 7, 1, 6, 4, 2, 11};

        System.out.println("---------- heapSort ----------");

        print(array);

        int len = array.length;

        buildHeap(array, len);

        for (int i = len - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            len--;
            heapify(array, 0, len);
        }

        print(array);
    }

    /**
     * 构建堆
     *
     * @param array
     * @param len
     */
    private static void buildHeap(int[] array, int len) {
        for (int i = (int) Math.floor(len / 2); i >= 0; i--) {
            heapify(array, i, len);
        }
    }

    /**
     * 堆调整
     *
     * @param array
     * @param i
     * @param len
     */
    private static void heapify(int[] array, int i, int len) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < len && array[left] > array[largest]) {
            largest = left;
        }

        if (right < len && array[right] > array[largest]) {
            largest = right;
        }

        if (largest != i) {
            int temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;

            heapify(array, largest, len);
        }
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

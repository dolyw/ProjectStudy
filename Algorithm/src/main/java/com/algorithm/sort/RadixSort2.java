package com.algorithm.sort;

/**
 * 基数排序 - 字符排序
 *
 * https://blog.csdn.net/qq_35344198/article/details/107615053
 * https://blog.csdn.net/lhj520cb/article/details/119456573
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/12/01 10:03
 */
public class RadixSort2 {

    /**
     * 基数排序算法
     *
     * @param args
     */
    public static void main(String[] args) {
        // 字符排序
        String[] strArray = {"cni", "efz", "omv", "kbv", "pl", "acj", "agd", "ef"};

        System.out.println("---------- radixSortStr1 ----------");
        radixSortStr1(strArray);
    }

    /**
     * 基数排序字符排序基础版本
     *
     * @param strArray
     */
    private static void radixSortStr1(String[] strArray) {
        print(strArray);

        // print(array);
    }

    /**
     * 打印数组
     *
     * @param strArray
     */
    private static void print(String[] strArray) {
        for (String value : strArray) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}

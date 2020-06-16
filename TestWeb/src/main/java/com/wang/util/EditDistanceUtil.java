package com.wang.util;

/**
 * 字符串相似度算法 - EditDistanceUtil算法(Levenshtein Distance算法)
 * 是指两个字符串之间，由一个转成另一个所需的最少编辑操作次数
 * 许可的编辑操作包括将一个字符替换成另一个字符，插入一个字符，删除一个字符
 * 一般来说，编辑距离越小，两个串的相似度越大
 * https://www.cnblogs.com/xiaoyulong/p/8846745.html
 *
 * @author Wang926454
 * @date 2018/9/18 18:10
 */
public class EditDistanceUtil {

    /**
     * 两字符串进行匹配返回矩阵
     */
    private static int compare(String str, String target) {
        // 矩阵
        int[][] d;
        int n = str.length();
        int m = target.length();
        // 遍历str的
        int i;
        // 遍历target的
        int j;
        // str的
        char ch1;
        // target的
        char ch2;
        // 记录相同字符，在某个矩阵位置值的增量，不是0就是1
        int temp;
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) {
            // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) {
            // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边 + 1，上边 + 1, 左上角 + temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    /**
     * 取三个值中最小的
     */
    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * 获取两字符串的相似度
     */
    public static float getSimilarityRatio(String str, String target) {
        return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
    }
}

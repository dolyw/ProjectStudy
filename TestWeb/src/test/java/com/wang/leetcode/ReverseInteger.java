package com.wang.leetcode;

import org.junit.jupiter.api.Test;

/**
 * TestWeb
 *
 * @author dolyw.com
 * @date 2019/8/7 14:21
 */
public class ReverseInteger {

    @Test
    public void Class() {
        System.out.println(reverse(146384748));
    }

    public int reverse(int x) {
        // 获得绝对值
        int n = Math.abs(x);
        // 反转
        String str = new StringBuilder(String.valueOf(n)).reverse().toString();
        try {
            return x > 0 ? Integer.parseInt(str) : -Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
        /*int flag = x < 0 ? -1 : 1;
        String xx = String.valueOf(Math.abs(x));
        String r = new StringBuilder(xx).reverse().toString();
        try {
            int result = flag * Integer.parseInt(r);
            return result;
        } catch (Exception e) {
            return 0;
        }*/
    }

}

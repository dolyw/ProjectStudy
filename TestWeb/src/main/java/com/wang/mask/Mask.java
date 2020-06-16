package com.wang.mask;

import java.util.Arrays;

/**
 * 掩码(脱敏)工具类
 */
public final class Mask {

    public static final char MASK_CHAR = '*';

    public static final char[] MASK_3 = new char[3];

    public static final char[] MASK_4 = new char[4];

    public static final char[] MASK_6 = new char[6];

    public static final char[] MASK_100 = new char[100];

    public static final char[] EMPTY_CHARS = new char[0];

    static {
        Arrays.fill(MASK_3, MASK_CHAR);
        Arrays.fill(MASK_4, MASK_CHAR);
        Arrays.fill(MASK_6, MASK_CHAR);
        Arrays.fill(MASK_100, MASK_CHAR);
    }

    private Mask() { }

    /**
     * 掩码处理工具，保留指定数量的字符，其他字符以 "*" 替代。
     *
     * @param str    原文字符串
     * @param before 原文中头部需要保留的字符数量
     * @param after  原文中尾部需要保留的字符数量
     * @return 掩码处理后的字符串。如果原文字符串长度小于等于头部与尾部保留的字符数量之和时，不作掩码处理
     */
    public static String mask(String str, int before, int after) {
        char[] chs = maskToChars(str, before, after);
        return toString(chs);
    }

    public static String mask(String str, int before, int after, int maskCount) {
        char[] chs = maskToChars(str, before, after, maskCount);
        return toString(chs);
    }

    public static char[] maskToChars(String str, int before, int after) {
        if (str == null) {
            return null;
        }
        char[] chs = str.toCharArray();
        if (chs.length == 0 || chs.length <= before + after) {
            return chs;
        }
        System.arraycopy(MASK_100, before, chs, before, chs.length - before - after);
        return chs;
    }

    public static char[] maskToChars(String str, int before, int after, int maskCount) {
        if (str == null) {
            return null;
        }
        if (maskCount <= 0) {
            return maskToChars(str, before, after);
        }
        char[] strs = str.toCharArray();
        if (strs.length == 0 || strs.length <= before + after) {
            return strs;
        }
        if (maskCount > MASK_100.length) {
            maskCount = MASK_100.length;
        }
        char[] chs = new char[before + after + maskCount];
        System.arraycopy(strs, 0, chs, 0, before);
        System.arraycopy(strs, strs.length - after, chs, chs.length - after, after);
        System.arraycopy(MASK_100, 0, chs, before, maskCount);
        return chs;
    }

    private static String toString(char[] chs) {
        if (chs == null) {
            return null;
        }
        return new String(chs);
    }
}

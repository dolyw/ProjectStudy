package com.wang.mask;

import java.util.regex.Pattern;

/**
 * 掩码(脱敏)类型
 *
 * @author frankiegao123
 */
public enum MaskType {

    /**
     * 默认的掩码(脱敏)类型
     */
    DEFAULT(5, 5, 3),

    /**
     * 手机号码
     */
    MOBILE(3, 4, 0),

    /**
     * 银行卡号
     */
    BANK_CARD(6, 4, 0),

    /**
     * 身份证号
     */
    ID_CARD(1, 1, 0),

    /**
     * 姓名
     */
    NAME(0, 0, 0) {
        @Override
        protected char[] internalMask(String str) {
            char[] chs = str.toCharArray();
            chs[0] = Mask.MASK_CHAR;
            return chs;
        }
    },

    /**
     * 信用卡 CVV
     */
    CVV(0, 0, 0) {
        @Override
        protected char[] internalMask(String str) {
            return Mask.MASK_3;
        }
    },

    /**
     * 信用卡有效期
     */
    CREDIT_EXP(0, 0, 0) {
        @Override
        protected char[] internalMask(String str) {
            return Mask.MASK_4;
        }
    },

    /**
     * 密码
     */
    PASSWORD(0, 0, 0) {
        @Override
        protected char[] internalMask(String str) {
            return Mask.MASK_6;
        }
    },

    /**
     * 验证码
     */
    CAPTCHA(0, 0, 0) {
        @Override
        protected char[] internalMask(String str) {
            return Mask.MASK_6;
        }
    },

    /**
     * 地址
     */
    ADDRESS(0, 0, 0) {
        private final Pattern PATTERN = Pattern.compile("[0-9一二三四五六七八九十百千万]++|[A-Za-z]+(?=\\s*[座区])");
        private final String mask = new String(Mask.MASK_3);

        @Override
        protected char[] internalMask(String str) {
            return PATTERN.matcher(str).replaceAll(mask).toCharArray();
        }
    },

    /**
     * Email 地址
     */
    EMAIL(0, 0, 0) {
        @Override
        protected char[] internalMask(String str) {
            char[] org = str.toCharArray();
            int at = str.indexOf('@');
            if (at < 2) {
                return org;
            }
            int len = org.length - at + 1;
            char[] chs = new char[len + 4];
            chs[0] = org[0];
            chs[1] = chs[2] = chs[3] = Mask.MASK_CHAR;
            System.arraycopy(org, at - 1, chs, 4, len);
            return chs;
        }
    },

    /**
     * IP 地址
     */
    IP_ADDR(0, 0, 0) {
        @Override
        protected char[] internalMask(String str) {
            char[] chs = str.toCharArray();
            boolean isMask = false;
            int offset = 0;
            for (int i = 0, d = 0; i < chs.length; i++) {
                if (chs[i] == '.') {
                    chs[offset++] = chs[i];
                    d++;
                    isMask = true;
                    continue;
                }
                if (d == 0 || d > 2) {
                    chs[offset++] = chs[i];
                    continue;
                }
                if (isMask) {
                    chs[offset++] = Mask.MASK_CHAR;
                    isMask = false;
                }
            }
            char[] ch = new char[offset];
            System.arraycopy(chs, 0, ch, 0, ch.length);
            return ch;
        }
    },

    GUESS(0, 0, 0) {
        @Override
        protected char[] internalMask(String str) {
            if (str.indexOf('@') >= 0) {
                return EMAIL.internalMask(str);
            }
            if (str.indexOf('.') >= 0) {
                return IP_ADDR.internalMask(str);
            }
            if (str.indexOf('号') >= 0) {
                return ADDRESS.internalMask(str);
            }
            switch (str.length()) {
                case 3:
                    return CVV.internalMask(str);
                case 4:
                    return CREDIT_EXP.internalMask(str);
                case 6:
                    return CAPTCHA.internalMask(str);
                case 11:
                    return MOBILE.internalMask(str);
                case 18:
                    return ID_CARD.internalMask(str);
                default:
                    break;
            }
            return DEFAULT.internalMask(str);
        }
    };

    /**
     * 掩码时开始需要保留的字符数量
     */
    private int before;

    /**
     * 掩码时结尾需要保留的字符数量
     */
    private int after;

    /**
     * 掩码字符的数量，若小于等于 0，则掩码字符的数量与原文数量一致
     */
    private int mask;

    private MaskType(int before, int after, int mask) {
        this.before = before;
        this.after = after;
        this.mask = mask;
    }

    /**
     * 对数据进行掩码
     *
     * @param str 待掩码的原文字符串
     * @return 掩码后的字符串
     */
    public final String mask(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return new String(internalMask(str));
    }

    /**
     * 对数据进行掩码
     *
     * @param str 待掩码的原文字符串
     * @return 掩码后的字节数组
     */
    public final char[] maskToChars(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return Mask.EMPTY_CHARS;
        }
        return internalMask(str);
    }

    protected char[] internalMask(String str) {
        return Mask.maskToChars(str, before, after, mask);
    }
}

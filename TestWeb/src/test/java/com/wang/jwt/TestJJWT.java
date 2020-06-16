package com.wang.jwt;

import com.wang.util.JjwtUtil;
import org.junit.jupiter.api.Test;

/**
 * JJWT
 *
 * @author Wang926454
 * @date 2018/8/31 9:51
 */
public class TestJJWT {

    /**
     * Redis中存的随机UUID
     */
    private static final String SECRETUUID = "gd3f24f3";

    /**
     * Token临时保存
     */
    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MzU2ODIzNTAsInN1YiI6IndhbmciLCJleHAiOjE1MzU2ODI2NTB9.76Y0cQKzb84S1F4sFrtTF-iFhSncjcCcSngI7Q1K7-Q";

    /**
     * 加密获取Token
     *
     * @param
     * @return void
     * @author Wang926454
     * @date 2018/8/31 9:54
     */
    @Test
    public void Test01() {
        String token = JjwtUtil.createJWT("wang", "wang926454" + SECRETUUID);
        System.out.println(token);
    }

    /**
     * 获取用户名
     *
     * @param
     * @return void
     * @author Wang926454
     * @date 2018/8/31 10:17
     */
    @Test
    public void Test02() {
        System.out.println(JjwtUtil.parseJWT(TOKEN, "wang926454" + SECRETUUID).getSubject());
    }

    /**
     * 认证Token
     *
     * @param
     * @return void
     * @author Wang926454
     * @date 2018/8/31 9:59
     */
    @Test
    public void Test03() {
        System.out.println(JjwtUtil.parseJWT(TOKEN, "wang926454" + SECRETUUID));
    }
}

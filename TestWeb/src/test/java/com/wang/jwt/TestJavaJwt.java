package com.wang.jwt;

import com.wang.util.JwtUtil;
import org.junit.jupiter.api.Test;

/**
 * Java-Jwt
 *
 * @author Wang926454
 * @date 2018/8/31 9:08
 */
public class TestJavaJwt {

    /**
     * Redis中存的随机UUID
     */
    private static final String SECRETUUID = "gd3f24f3";

    /**
     * Token临时保存
     */
    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MzU3MDAyNDksImFjY291bnQiOiJ3YW5nIn0.G7poDis-4k21UD7t25nklTjvA3REVngFX6bIWsThT6o";

    /**
     * 加密获取Token
     *
     * @param
     * @return void
     * @author Wang926454
     * @date 2018/8/31 9:13
     */
    @Test
    public void Test01() {
        String token = JwtUtil.sign("wang", "wang926454" + SECRETUUID);
        System.out.println(token);
    }

    /**
     * 获取用户名
     *
     * @param
     * @return void
     * @author Wang926454
     * @date 2018/8/31 9:13
     */
    @Test
    public void Test02() {
        String account = JwtUtil.getClaim(TOKEN, "account");
        System.out.println(account);
    }

    /**
     * 认证Token
     *
     * @param
     * @return void
     * @author Wang926454
     * @date 2018/8/31 9:13
     */
    @Test
    public void Test03() {
        boolean flag = JwtUtil.verify(TOKEN, "wang926454" + SECRETUUID);
        System.out.println(flag);
    }

}

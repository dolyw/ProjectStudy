package com.wang.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JJWT工具类
 *
 * @author Wang926454
 * @date 2018/8/31 9:47
 */
public class JjwtUtil {
    /**
     * 过期时间-5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param jsonWebToken
     * @param base64Security
     * @return io.jsonwebtoken.Claims
     * @author Wang926454
     * @date 2018/8/31 10:03
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(base64Security.getBytes())
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    /**
     * 生成签名，获取Token
     *
     * @param username
     * @param base64Security
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/8/31 10:03
     */
    public static String createJWT(String username, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setIssuedAt(now)
                .setSubject(username)
                .signWith(signatureAlgorithm, base64Security.getBytes());
        // 设置过期时间
        if (EXPIRE_TIME >= 0) {
            long expMillis = nowMillis + EXPIRE_TIME;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        // 生成JWT
        return builder.compact();
    }
}

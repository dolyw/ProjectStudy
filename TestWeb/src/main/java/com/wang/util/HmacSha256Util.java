package com.wang.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HmacSHA256加密
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/16 14:28
 */
public class HmacSha256Util {

    /**
     * 字节转字符
     *
     * @param b
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/16 14:28
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(stmp);
        }
        return stringBuilder.toString().toLowerCase();
    }


    /**
     * HmacSHA256加密
     *
     * @param message 加密内容
	 * @param secret 加密私钥
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/16 14:29
     */
    public static String hmacSha256(String message, String secret) {
        String hash = "";
        try {
            Mac hmacSha256Mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            hmacSha256Mac.init(secretKeySpec);
            byte[] bytes = hmacSha256Mac.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
            e.printStackTrace();
        }
        return hash;
    }
}

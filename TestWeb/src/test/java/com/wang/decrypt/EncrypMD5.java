package com.wang.decrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5
 *
 * @author Wang926454
 * @date 2018/8/21 15:05
 */
public class EncrypMD5 {

    /**
     * 加密
     *
     * @param info
     * @return byte[]
     * @author Wang926454
     * @date 2018/8/21 15:14
     */
    public byte[] eccrypt(String info) throws NoSuchAlgorithmException {
        //根据MD5算法生成MessageDigest对象
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] srcBytes = info.getBytes();
        //使用srcBytes更新摘要
        md5.update(srcBytes);
        //完成哈希计算，得到result
        byte[] resultBytes = md5.digest();
        return resultBytes;
    }

    /**
     * 测试
     *
     * @param args
     * @return void
     * @author Wang926454
     * @date 2018/8/21 15:09
     */
    public static void main(String args[]) throws NoSuchAlgorithmException {
        String msg = "dhdslkaflkf";
        EncrypMD5 md5 = new EncrypMD5();
        byte[] resultBytes = md5.eccrypt(msg);

        System.out.println("密文是：" + new String(resultBytes));
        System.out.println("明文是：" + msg);
    }

}

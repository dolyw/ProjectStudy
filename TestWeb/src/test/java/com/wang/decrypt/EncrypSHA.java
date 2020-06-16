package com.wang.decrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA
 *
 * @author Wang926454
 * @date 2018/8/21 15:08
 */
public class EncrypSHA {

    /**
     * 加密
     *
     * @param info
     * @return byte[]
     * @author Wang926454
     * @date 2018/8/21 15:14
     */
    public byte[] eccrypt(String info) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("SHA");
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
     * @date 2018/8/21 15:14
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String msg = "dhdslkaflkf";
        EncrypSHA sha = new EncrypSHA();
        byte[] resultBytes = sha.eccrypt(msg);
        System.out.println("明文是：" + msg);
        System.out.println("密文是：" + new String(resultBytes));
    }

}

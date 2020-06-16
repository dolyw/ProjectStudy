package com.wang.decrypt;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * DES
 *
 * @author Wang926454
 * @date 2018/8/21 15:20
 */
public class EncrypDES {
    // KeyGenerator 提供对称密钥生成器的功能，支持各种算法
    private KeyGenerator keygen;
    // SecretKey 负责保存对称密钥
    private SecretKey deskey;
    // Cipher负责完成加密或解密工作
    private Cipher c;
    // 该字节数组负责保存加密的结果
    private byte[] cipherByte;

    public EncrypDES() throws NoSuchAlgorithmException, NoSuchPaddingException {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        // 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
        keygen = KeyGenerator.getInstance("DES");
        // 生成密钥
        deskey = keygen.generateKey();
        // 生成Cipher对象,指定其支持的DES算法
        c = Cipher.getInstance("DES");
    }

    /**
     * 加密
     *
     * @param str
     * @return byte[]
     * @author Wang926454
     * @date 2018/8/21 15:21
     */
    public byte[] Encrytor(String str) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
        c.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] src = str.getBytes();
        // 加密，结果保存进cipherByte
        cipherByte = c.doFinal(src);
        return cipherByte;
    }

    /**
     * 解密
     *
     * @param buff
     * @return byte[]
     * @author Wang926454
     * @date 2018/8/21 15:21
     */
    public byte[] Decryptor(byte[] buff) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示解密模式
        c.init(Cipher.DECRYPT_MODE, deskey);
        cipherByte = c.doFinal(buff);
        return cipherByte;
    }

    /**
     * 测试
     *
     * @param args
     * @return void
     * @author Wang926454
     * @date 2018/8/21 15:21
     */
    public static void main(String[] args) throws Exception {
        EncrypDES de1 = new EncrypDES();
        String msg = "dsa";
        byte[] encontent = de1.Encrytor(msg);
        byte[] decontent = de1.Decryptor(encontent);
        System.out.println("明文是:" + msg);
        System.out.println("加密后:" + EncrypBase64.encode(new String(encontent)));
        System.out.println("解密后:" + new String(decontent));
    }

}

package jvm.reference;

import java.io.IOException;

/**
 * 强引用
 *
 * 只要有引用就不会被回收
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 17:00
 */
public class T01_NormalReference {

    public static void main(String[] args) {
        Custom custom = new Custom();
        custom = null;
        // 垃圾回收，DisableExplicitGC
        System.gc();
        try {
            // 程序停止
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

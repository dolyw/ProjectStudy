package com.design.singleton;

/**
 * 单例模式
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/26 10:47
 */
public class Main {

    public static void main(String[] args) {
        // 私有构造，不能直接New
        // HungrySingleton hungrySingleton = new HungrySingleton();

        // 饿汉式
        HungrySingleton hungrySingleton1 = HungrySingleton.getInstance();
        HungrySingleton hungrySingleton2 = HungrySingleton.getInstance();
        System.out.println(hungrySingleton1 == hungrySingleton2);
        // 懒汉式
        LazySingleton lazySingleton1 = LazySingleton.getInstance();
        LazySingleton lazySingleton2 = LazySingleton.getInstance();
        System.out.println(lazySingleton1 == lazySingleton2);
        // 双检锁/双重校验锁(DCL，即 Double-Checked Locking)
        DclSingleton dclSingleton1 = DclSingleton.getInstance();
        DclSingleton dclSingleton2 = DclSingleton.getInstance();
        System.out.println(dclSingleton1 == dclSingleton2);
        // 登记式/静态内部类
        InnerSingleton innerSingleton1 = InnerSingleton.getInstance();
        InnerSingleton innerSingleton2 = InnerSingleton.getInstance();
        System.out.println(innerSingleton1 == innerSingleton2);
        // 枚举
        System.out.println(EnumSingleton.INSTANCE == EnumSingleton.INSTANCE);
    }

}

package com.design.singleton;

/**
 * 单例-饿汉式
 *
 * 优点：没有加锁，执行效率会提高
 * 缺点：类加载时就初始化，浪费内存
 *
 * 一般有这个类肯定会用到，所以浪费内存还是比较少见的
 *
 * 它基于ClassLoader机制避免了多线程的同步问题，不过INSTANCE在类装载时就实例化，
 * 虽然导致类装载的原因有很多种，在单例模式中大多数都是调用getInstance()方法，
 * 但是也不能确定有其他的方式（或者其他的静态方法）导致类装载，
 * 这时候初始化INSTANCE显然没有达到Lazy Loading的效果，所以就衍生出了懒汉式
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/26 10:26
 */
public class HungrySingleton {

    /**
     * 单例-INSTANCE
     */
    private static final HungrySingleton INSTANCE = new HungrySingleton();
    /*private static final HungrySingleton INSTANCE;

    static {
        INSTANCE = new HungrySingleton();
    }*/

    /**
     * 私有构造，不能直接New
     */
    private HungrySingleton() {}

    /**
     * 单例获取静态方法
     *
     * @param
     * @return com.design.singleton.HungrySingleton
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/26 10:50
     */
    public static HungrySingleton getInstance() {
        return INSTANCE;
    }

}

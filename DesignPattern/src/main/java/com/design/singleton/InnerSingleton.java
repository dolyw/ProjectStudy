package com.design.singleton;

/**
 * 单例-登记式/静态内部类
 *
 * 优点：第一次调用才初始化，避免内存浪费
 *
 * 这种方式能达到双检锁方式一样的功效，但实现更简单。
 * 对静态域使用延迟初始化，应使用这种方式而不是双检锁方式。
 * 这种方式只适用于静态域的情况，双检锁方式可在实例域需要延迟初始化时使用。
 *
 * 这种方式同样利用了ClassLoader机制来保证初始化INSTANCE时只有一个线程，
 * 这种方式的好处是InnerSingleton类被装载了，INSTANCE不一定被初始化。
 * 因为SingletonHolder类没有被主动使用，只有通过显式调用getInstance方法时，
 * 才会显式装载SingletonHolder类，从而实例化INSTANCE。
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/26 11:30
 */
public class InnerSingleton {

    /**
     * 私有构造，不能直接New
     */
    private InnerSingleton() { }

    /**
     * 静态内部类
     *
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/26 11:33
     */
    private static class SingletonHolder {

        /**
         * 单例-INSTANCE
         */
        private static final InnerSingleton INSTANCE = new InnerSingleton();

    }

    /**
     * 单例获取静态方法
     *
     * @param
     * @return com.design.singleton.InnerSingleton
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/26 10:50
     */
    public static InnerSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

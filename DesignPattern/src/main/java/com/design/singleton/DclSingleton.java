package com.design.singleton;

/**
 * 单例-双检锁/双重校验锁(DCL，即 Double-Checked Locking)
 *
 * 优点：第一次调用才初始化，避免内存浪费
 *
 * 这种方式采用双锁机制，安全且在多线程情况下能保持高性能
 * getInstance()的性能对应用程序很关键
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/26 10:26
 */
public class DclSingleton {

    /**
     * 单例-INSTANCE-加volatile
     */
    private static volatile DclSingleton INSTANCE;

    /**
     * 私有构造，不能直接New
     */
    private DclSingleton() {}

    /**
     * 单例获取静态方法
     *
     * @param
     * @return com.design.singleton.DclSingleton
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/26 10:50
     */
    public static DclSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (DclSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DclSingleton();
                }
            }
        }
        return INSTANCE;
    }

}

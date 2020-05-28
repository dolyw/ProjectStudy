package com.design.strategy;

/**
 * 环境(Context)类：持有一个策略类的引用，最终给客户端调用
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/26 16:24
 */
public class GoContext {

    private GoStrategy goStrategy;

    public GoContext(GoStrategy goStrategy) {
        this.goStrategy = goStrategy;
    }

    /**
     * 执行策略
     *
     * @param name
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/26 16:28
     */
    public void executeStrategy(String name) {
        goStrategy.go(name);
    }
}

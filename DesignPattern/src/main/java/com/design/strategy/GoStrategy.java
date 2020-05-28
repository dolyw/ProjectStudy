package com.design.strategy;

/**
 * 抽象策略-GoStrategy出行方式策略接口
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/26 16:19
 */
public interface GoStrategy {

    /**
     * 出行方式
     *
     * @param name
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/26 16:27
     */
    void go(String name);

}

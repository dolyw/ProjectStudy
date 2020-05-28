package com.design.strategy.impl;

import com.design.strategy.GoStrategy;

/**
 * 具体策略-Plane飞机策略
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/26 16:20
 */
public class PlaneStrategyImpl implements GoStrategy {

    @Override
    public void go(String name) {
        System.out.println(name + " Plane Go");
    }

}

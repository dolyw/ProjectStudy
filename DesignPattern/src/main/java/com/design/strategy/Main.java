package com.design.strategy;

import com.design.strategy.impl.BusStrategyImpl;
import com.design.strategy.impl.CarStrategyImpl;
import com.design.strategy.impl.PlaneStrategyImpl;

/**
 * 策略模式
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/26 16:23
 */
public class Main {

    public static void main(String[] args) {
        // WangMing家没钱，公交车策略
        GoContext goContext = new GoContext(new BusStrategyImpl());
        goContext.executeStrategy("WangMing");
        // WangXiaoHong家小康，小汽车策略
        goContext = new GoContext(new CarStrategyImpl());
        goContext.executeStrategy("WangXiaoHong");
        // WangXiaoLi家有钱，飞机策略
        goContext = new GoContext(new PlaneStrategyImpl());
        goContext.executeStrategy("WangXiaoLi");
    }

}

package com.design.templatemethod;

/**
 * 坦克大战的实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 10:00
 */
public class TankGameImpl extends Game {
    @Override
    void initialize() {
        System.out.println("坦克大战初始化");
    }

    @Override
    void startPlay() {
        System.out.println("坦克大战开始游戏");
    }

    @Override
    void endPlay() {
        System.out.println("坦克大战结束游戏");
    }
}

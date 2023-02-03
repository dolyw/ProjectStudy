package com.design.templatemethod;

/**
 * 贪吃蛇的实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 10:00
 */
public class SnakeGameImpl extends Game {
    @Override
    void initialize() {
        System.out.println("贪吃蛇初始化");
    }

    @Override
    void startPlay() {
        System.out.println("贪吃蛇开始游戏");
    }

    @Override
    void endPlay() {
        System.out.println("贪吃蛇结束游戏");
    }
}

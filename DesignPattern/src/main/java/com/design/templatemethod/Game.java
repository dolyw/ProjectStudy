package com.design.templatemethod;

/**
 * 模板方法 - 创建一个抽象类，它的模板方法被设置为 final
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 10:00
 */
public abstract class Game {

    /**
     * 初始化
     */
    abstract void initialize();

    /**
     * 开始游戏
     */
    abstract void startPlay();

    /**
     * 结束游戏
     */
    abstract void endPlay();

    /**
     * 模板
     */
    public final void play() {
        // 初始化游戏
        initialize();

        // 开始游戏
        startPlay();

        // 结束游戏
        endPlay();
    }
}
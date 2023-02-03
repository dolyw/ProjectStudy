package com.design.templatemethod;

/**
 * 模板方法
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 10:02
 */
public class Main {

    public static void main(String[] args) {
        // 使用 Game 的模板方法 play() 来演示游戏的定义方式
        Game tankGame = new TankGameImpl();
        tankGame.play();
        System.out.println("------------");
        Game snakeGame = new SnakeGameImpl();
        snakeGame.play();
    }

}

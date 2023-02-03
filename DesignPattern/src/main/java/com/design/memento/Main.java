package com.design.memento;

/**
 * 备忘录模式 - 游戏存档，浏览器历史记录
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 10:45
 */
public class Main {

    public static void main(String[] args) {
        GameCareTaker gameCareTaker = new GameCareTaker();
        // 开始游戏
        GameOriginator gameOriginator = new GameOriginator("1# 你出生了，你正在新手村出生位置");
        System.out.println(gameOriginator.getState());
        // 游戏存档
        gameCareTaker.add(gameOriginator.saveMemento());
        gameOriginator.setState("2# 去找村长对话");
        System.out.println(gameOriginator.getState());
        // 游戏存档
        gameCareTaker.add(gameOriginator.saveMemento());
        gameOriginator.setState("3# 获得任务，杀死史莱姆");
        System.out.println(gameOriginator.getState());
        // 游戏读取存档
        gameOriginator.getFromMemento(gameCareTaker.get(1));
        System.out.println(gameOriginator.getState());
        // 游戏读取存档
        gameOriginator.getFromMemento(gameCareTaker.get(0));
        System.out.println(gameOriginator.getState());
    }

}

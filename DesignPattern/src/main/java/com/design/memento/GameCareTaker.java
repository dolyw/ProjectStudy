package com.design.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏记录管理
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 10:45
 */
public class GameCareTaker {

    private List<GameMemento> gameMementoList = new ArrayList<>();

    public void add(GameMemento gameMemento){
        gameMementoList.add(gameMemento);
    }

    public GameMemento get(int index){
        return gameMementoList.get(index);
    }

}

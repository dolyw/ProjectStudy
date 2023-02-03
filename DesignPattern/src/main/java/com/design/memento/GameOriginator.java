package com.design.memento;

/**
 * 当前游戏状态
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 10:45
 */
public class GameOriginator {

    private String state;

    public GameOriginator(String state) {
        this.state = state;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

    public GameMemento saveMemento(){
        return new GameMemento(state);
    }

    public void getFromMemento(GameMemento gameMemento){
        state = gameMemento.getState();
    }

}

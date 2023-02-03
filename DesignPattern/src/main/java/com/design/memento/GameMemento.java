package com.design.memento;

/**
 * 游戏记录
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 10:45
 */
public class GameMemento {

    private String state;

    public GameMemento(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

}

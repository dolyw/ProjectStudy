package com.design.command;

/**
 * 使用命令对象的入口
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 16:05
 */
public class Invoker {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action(){
        this.command.execute();
    }

}

package com.design.command;

/**
 * 抽象命令
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 16:05
 */
public abstract class Command {

    protected RadioReceived radioReceived = new RadioReceived();

    public abstract void execute();

}

package com.design.command;

/**
 * 启动收音机具体命令
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 16:05
 */
public class StartCommandImpl extends Command {
    @Override
    public void execute() {
        super.radioReceived.start();
    }
}

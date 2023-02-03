package com.design.command;

/**
 * 切换频道具体命令
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 16:05
 */
public class NextCommandImpl extends Command {
    @Override
    public void execute() {
        super.radioReceived.next();
    }
}

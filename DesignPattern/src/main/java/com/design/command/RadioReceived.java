package com.design.command;

/**
 * 真正的命令执行对象
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 16:05
 */
public class RadioReceived {

    public void start() {
        System.out.println("启动收音机");
    }

    public void next() {
        System.out.println("切换频道");
    }

    public void shutdown() {
        System.out.println("关闭收音机");
    }

}

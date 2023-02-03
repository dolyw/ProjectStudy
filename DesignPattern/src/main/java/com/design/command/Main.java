package com.design.command;

/**
 * 命令模式
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 16:05
 */
public class Main {

    public static void main(String[] args) {
        // 定义调用者
        Invoker invoker = new Invoker();
        // 创建命令，启动收音机
        Command command = new StartCommandImpl();
        // 调用者接受命令
        invoker.setCommand(command);
        // 执行命令
        invoker.action();
        // 换下个频道
        command = new NextCommandImpl();
        invoker.setCommand(command);
        invoker.action();
        // 关闭收音机
        command = new ShutdownCommandImpl();
        invoker.setCommand(command);
        invoker.action();
    }

}

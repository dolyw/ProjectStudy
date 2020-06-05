package com.design.mediator.v3;

/**
 * 抽象同事类-SubSystem-子系统
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 14:33
 */
public abstract class AbstractSubSystem {

    protected AbstractMediator mediator;

    public void setMediator(AbstractMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * 发送消息
     *
     * @param message
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/5 14:54
     */
    public abstract void send(String message);

    /**
     * 接收消息
     *
     * @param message
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/5 14:54
     */
    public abstract void receive(String message);
}

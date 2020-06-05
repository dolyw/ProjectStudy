package com.design.mediator.v3;

/**
 * 抽象中介者
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 15:22
 */
public abstract class AbstractMediator {

    /**
     * 注册子系统
     *
     * @param subSystem
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/5 14:37
     */
    public abstract void register(AbstractSubSystem subSystem);

    /**
     * 当前系统转发消息到其他系统
     *
     * @param subSystem
     * @param message
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/5 15:24
     */
    public abstract void relay(AbstractSubSystem subSystem, String message);

}

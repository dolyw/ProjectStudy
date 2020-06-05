package com.design.mediator.v2;

/**
 * SubSystem-子系统
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 14:33
 */
public interface SubSystem {

    /**
     * 发送消息
     *
     * @param message
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/5 14:54
     */
    void send(String message);

    /**
     * 接收消息
     *
     * @param message
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/5 14:54
     */
    void receive(String message);
}

package com.design.mediator.v3;

/**
 * 具体同事类-CustomSubSystem2-子系统2
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 15:27
 */
public class CustomSubSystem2 extends AbstractSubSystem {
    @Override
    public void send(String message) {
        mediator.relay(this, message);
    }

    @Override
    public void receive(String message) {
        System.out.println("CustomSubSystem2 receive:" + message);
    }
}

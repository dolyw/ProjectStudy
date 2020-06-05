package com.design.mediator.v3;

/**
 * 具体同事类-CustomSubSystem1-子系统1
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 15:27
 */
public class CustomSubSystem1 extends AbstractSubSystem {
    @Override
    public void send(String message) {
        mediator.relay(this, message);
    }

    @Override
    public void receive(String message) {
        System.out.println("CustomSubSystem1 receive:" + message);
    }
}

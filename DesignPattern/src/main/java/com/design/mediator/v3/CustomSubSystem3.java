package com.design.mediator.v3;

/**
 * 具体同事类-CustomSubSystem3-子系统3
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 15:27
 */
public class CustomSubSystem3 extends AbstractSubSystem {
    @Override
    public void send(String message) {
        mediator.relay(this, message);
    }

    @Override
    public void receive(String message) {
        System.out.println("CustomSubSystem3 receive:" + message);
    }
}

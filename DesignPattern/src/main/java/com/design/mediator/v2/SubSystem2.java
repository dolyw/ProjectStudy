package com.design.mediator.v2;

/**
 * SubSystem2-子系统2
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 10:23
 */
public class SubSystem2 implements SubSystem {

    public SubSystem2() {
        // 注册到中介者
        SimpleMediator simpleMediator = SimpleMediator.getInstance();
        simpleMediator.register(this);
    }

    @Override
    public void send(String message) {
        SimpleMediator simpleMediator = SimpleMediator.getInstance();
        simpleMediator.relay(this, message);
    }

    @Override
    public void receive(String message) {
        System.out.println("SubSystem2 receive:" + message);
    }

}

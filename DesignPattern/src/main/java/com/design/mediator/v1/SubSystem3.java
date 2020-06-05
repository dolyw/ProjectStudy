package com.design.mediator.v1;

/**
 * SubSystem3-子系统3
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 10:23
 */
public class SubSystem3 {

    public void send(String message) {
        SubSystem2 subSystem2 = new SubSystem2();
        SubSystem3 subSystem3 = new SubSystem3();
        subSystem2.receive(message);
        subSystem3.receive(message);
    }

    public void receive(String message) {
        System.out.println("SubSystem3 receive:" + message);
    }

}

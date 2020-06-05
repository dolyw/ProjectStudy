package com.design.mediator.v1;

/**
 * SubSystem2-子系统2
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 10:23
 */
public class SubSystem2 {

    public void send(String message) {
        SubSystem1 subSystem1 = new SubSystem1();
        SubSystem3 subSystem3 = new SubSystem3();
        subSystem1.receive(message);
        subSystem3.receive(message);
    }

    public void receive(String message) {
        System.out.println("SubSystem2 receive:" + message);
    }

}

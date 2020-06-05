package com.design.facade.v1;

/**
 * Client1-客户端1-未使用外观模式，客户端直接调用子系统
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 10:22
 */
public class Client1 {

    public void m() {
        SubSystem1 subSystem1 = new SubSystem1();
        SubSystem2 subSystem2 = new SubSystem2();
        SubSystem3 subSystem3 = new SubSystem3();

        subSystem1.toDo();
        subSystem2.toDo();
        subSystem3.toDo();
    }

}

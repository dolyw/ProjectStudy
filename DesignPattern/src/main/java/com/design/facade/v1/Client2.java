package com.design.facade.v1;

/**
 * Client2-客户端2
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 10:22
 */
public class Client2 {

    public void m() {
        SubSystem3 subSystem3 = new SubSystem3();
        SubSystem2 subSystem2 = new SubSystem2();
        SubSystem1 subSystem1 = new SubSystem1();

        subSystem3.toDo();
        subSystem2.toDo();
        subSystem1.toDo();
    }

}

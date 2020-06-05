package com.design.facade.v2;

/**
 * Client2-客户端2
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 10:22
 */
public class Client2 {

    public void m() {
        Facade facade = new Facade();
        facade.toDo3();
        facade.toDo2();
        facade.toDo1();
    }

}

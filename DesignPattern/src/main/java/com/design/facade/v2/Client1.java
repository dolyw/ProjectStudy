package com.design.facade.v2;

/**
 * Client1-客户端1-未使用外观模式，客户端直接调用子系统
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 10:22
 */
public class Client1 {

    public void m() {
        Facade facade = new Facade();
        facade.toDo1();
        facade.toDo2();
        facade.toDo3();
    }

}

package com.design.mediator.v3;

/**
 * 中介者模式(调停模式) - 使用抽象中介者模式调用
 *
 * 这种模式提供了一个中介类，该类通常处理不同类之间的通信，并支持松耦合，使代码易于维护
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 15:32
 */
public class Main {

    public static void main(String[] args) {
        AbstractMediator mediator = new CustomMediator();
        CustomSubSystem1 customSubSystem1 = new CustomSubSystem1();
        CustomSubSystem2 customSubSystem2 = new CustomSubSystem2();
        CustomSubSystem3 customSubSystem3 = new CustomSubSystem3();
        // 注册
        mediator.register(customSubSystem1);
        mediator.register(customSubSystem2);
        mediator.register(customSubSystem3);
        customSubSystem1.send("h1");
        System.out.println("-----");
        customSubSystem2.send("h2");
        System.out.println("-----");
        customSubSystem3.send("h3");
    }

}

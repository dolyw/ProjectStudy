package com.design.state;

/**
 * 状态模式 - 状态机的扭转
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 15:29
 */
public class Main {

    public static void main(String[] args) {
        // 声明环境类
        OrderContext orderContext = new OrderContext();
        // 初始化状态
        OrderState orderState = new OrderCreateStateImpl();
        // OrderState orderState = new OrderPayStateImpl();
        orderState.handle(orderContext);
        // 状态机扭转
        orderContext.handle();
        orderContext.handle();
        orderContext.handle();
        orderContext.handle();
        orderContext.handle();
    }

}

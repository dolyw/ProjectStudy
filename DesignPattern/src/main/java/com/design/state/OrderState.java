package com.design.state;

/**
 * 订单抽象状态
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 15:12
 */
public abstract class OrderState {

    public abstract void handle(OrderContext orderContext);

}

package com.design.state;

/**
 * 订单环境类
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 15:12
 */
public class OrderContext {

    private OrderState orderState;

    /**
     * 处理方法
     */
    public void handle() {
        orderState.handle(this);
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}

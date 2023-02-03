package com.design.state;

/**
 * 订单完成具体状态
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 15:12
 */
public class OrderFinishStateImpl extends OrderState {

    @Override
    public void handle(OrderContext orderContext) {
        System.out.println("完成");
        // 可以和享元模式组合，不需要每次都去new具体状态，可以创建一次后重复使用
        orderContext.setOrderState(new OrderCreateStateImpl());
        // 结尾状态设置为this，再次扭转永远都是当前状态
        // orderContext.setOrderState(this);
    }
}

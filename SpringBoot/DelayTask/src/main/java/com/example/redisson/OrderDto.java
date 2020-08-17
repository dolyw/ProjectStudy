package com.example.redisson;

import java.io.Serializable;

/**
 * OrderDto
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/14 16:31
 */
public class OrderDto implements Serializable {

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 订单名
     */
    private String orderName;

    public OrderDto(String orderCode) {
        this.orderCode = orderCode;
    }

    public OrderDto(String orderCode, String orderName) {
        this.orderCode = orderCode;
        this.orderName = orderName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}

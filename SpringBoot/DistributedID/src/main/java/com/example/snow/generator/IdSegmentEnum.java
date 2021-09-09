package com.example.snow.generator;

/**
 * ID号段，枚举类
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/8/30 10:08
 */
public enum IdSegmentEnum {

    /**
     * OrderCode-订单号号段
     */
    ORDER_CODE("22", "订单号号段"),

    /**
     * UserId-用户ID号段
     */
    USER_ID("11", "用户ID号段");

    private String segment;

    private String name;

    IdSegmentEnum(final String segment, final String name) {
        this.segment = segment;
        this.name = name;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

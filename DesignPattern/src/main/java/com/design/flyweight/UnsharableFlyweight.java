package com.design.flyweight;

/**
 * 非享元角色
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/24 14:14
 */
public class UnsharableFlyweight {

    private String msg;

    public UnsharableFlyweight(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}

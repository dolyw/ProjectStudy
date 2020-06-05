package com.design.facade.v1;

/**
 * 外观模式(门面模式) - 未使用外观模式，客户端直接调用子系统
 *
 * 隐藏系统的复杂性，并向客户端提供了一个客户端可以访问系统的接口
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 10:26
 */
public class Main {

    public static void main(String[] args) {
        Client1 client1 = new Client1();
        Client2 client2 = new Client2();
        client1.m();
        client2.m();
    }

}

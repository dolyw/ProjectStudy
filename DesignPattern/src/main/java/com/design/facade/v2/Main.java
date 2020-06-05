package com.design.facade.v2;

/**
 * 外观模式(门面模式) - 使用Facade，外观门面处理内部调用的问题，
 * 客户端只和外观门面打交道就行，这样更加安全，而且客户端使用更简单
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

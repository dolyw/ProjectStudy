package com.design.factorymethod.product.impl;

import com.design.factorymethod.product.Animal;

/**
 * 工厂方法-具体产品-牛类
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 11:26
 */
public class CattleImpl implements Animal {

    @Override
    public void show() {
        System.out.println("CattleImpl Show");
    }

}

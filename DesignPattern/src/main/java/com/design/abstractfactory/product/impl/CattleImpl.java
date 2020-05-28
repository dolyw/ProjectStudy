package com.design.abstractfactory.product.impl;

import com.design.abstractfactory.product.Animal;

/**
 * 抽象工厂-具体动物类产品-牛类
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

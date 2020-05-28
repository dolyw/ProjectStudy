package com.design.abstractfactory.product.impl;

import com.design.abstractfactory.product.Animal;

/**
 * 抽象工厂-具体动物类产品-马类
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 11:26
 */
public class HorseImpl implements Animal {

    @Override
    public void show() {
        System.out.println("HorseImpl Show");
    }

}

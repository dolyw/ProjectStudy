package com.design.abstractfactory.product.impl;

import com.design.abstractfactory.product.Plant;

/**
 * 抽象工厂-具体植物类产品-蔬菜类
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 15:43
 */
public class VegetablesImpl implements Plant {
    @Override
    public void show() {
        System.out.println("VegetablesImpl Show");
    }
}

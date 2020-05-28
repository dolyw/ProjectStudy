package com.design.factorymethod.factory.impl;

import com.design.factorymethod.factory.AbstractFactory;
import com.design.factorymethod.product.Animal;
import com.design.factorymethod.product.impl.HorseImpl;

/**
 * 工厂方法-具体工厂-实现了马类的生成方法
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 11:43
 */
public class HorseFactoryImpl implements AbstractFactory {

    @Override
    public Animal getAnimal() {
        return new HorseImpl();
    }

}

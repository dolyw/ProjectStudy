package com.design.abstractfactory.factory.impl;

import com.design.abstractfactory.factory.AbstractFarmFactory;
import com.design.abstractfactory.product.Animal;
import com.design.abstractfactory.product.Plant;
import com.design.abstractfactory.product.impl.FruitImpl;
import com.design.abstractfactory.product.impl.HorseImpl;

/**
 * 抽象工厂-具体工厂-长沙农场-生产马和水果
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 11:43
 */
public class ChangshaFarmFactoryImpl implements AbstractFarmFactory {

    @Override
    public Animal getAnimal() {
        return new HorseImpl();
    }

    @Override
    public Plant getPlant() {
        return new FruitImpl();
    }
}

package com.design.abstractfactory.factory.impl;

import com.design.abstractfactory.factory.AbstractFarmFactory;
import com.design.abstractfactory.product.Animal;
import com.design.abstractfactory.product.Plant;
import com.design.abstractfactory.product.impl.CattleImpl;
import com.design.abstractfactory.product.impl.VegetablesImpl;

/**
 * 抽象工厂-具体工厂-深圳农场-生产牛和蔬菜
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 11:43
 */
public class ShenzhenFarmFactoryImpl implements AbstractFarmFactory {

    @Override
    public Animal getAnimal() {
        return new CattleImpl();
    }

    @Override
    public Plant getPlant() {
        return new VegetablesImpl();
    }
}

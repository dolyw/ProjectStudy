package com.design.abstractfactory;

import com.design.abstractfactory.factory.AbstractFarmFactory;
import com.design.abstractfactory.factory.impl.ShenzhenFarmFactoryImpl;
import com.design.abstractfactory.factory.impl.ChangshaFarmFactoryImpl;

/**
 * 抽象工厂
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 11:34
 */
public class Main {

    public static void main(String[] args) {
        // 抽象工厂应用
        AbstractFarmFactory farmFactory = null;
        farmFactory = new ChangshaFarmFactoryImpl();
        farmFactory.getAnimal().show();
        farmFactory.getPlant().show();
        farmFactory = new ShenzhenFarmFactoryImpl();
        farmFactory.getAnimal().show();
        farmFactory.getPlant().show();
    }

}

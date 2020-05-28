package com.design.factorymethod;

import com.design.factorymethod.factory.AbstractFactory;
import com.design.factorymethod.factory.SimpleStaticFactory;
import com.design.factorymethod.factory.impl.CattleFactoryImpl;
import com.design.factorymethod.factory.impl.HorseFactoryImpl;
import com.design.factorymethod.product.Animal;

/**
 * 工厂方法
 *
 * 工厂方法模式中我们把生成产品类的时间延迟，就是通过对应的工厂类来生成对应的产品类，
 * 在这里我们就可以实现“开发-封闭”原则，无论加多少产品类，
 * 我们都不用修改原来类中的代码，而是通过增加工厂类来实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 11:34
 */
public class Main {

    public static void main(String[] args) {
        // 简单工厂(静态工厂)应用
        Animal cattle = SimpleStaticFactory.getAnimal(SimpleStaticFactory.CATTLE);
        cattle.show();
        Animal horse = SimpleStaticFactory.getAnimal(SimpleStaticFactory.HORSE);
        horse.show();
        // 工厂方法应用
        AbstractFactory factory = null;
        factory = new CattleFactoryImpl();
        factory.getAnimal().show();
        factory = new HorseFactoryImpl();
        factory.getAnimal().show();
    }

}

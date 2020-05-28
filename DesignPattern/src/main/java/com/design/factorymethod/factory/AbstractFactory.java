package com.design.factorymethod.factory;

import com.design.factorymethod.product.Animal;

/**
 * 工厂方法-抽象工厂-提供了动物的生成方法
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 11:42
 */
public interface AbstractFactory {

    /**
     * 获取产品
     *
     * @param
     * @return com.design.factorymethod.product.Animal
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/28 11:42
     */
    Animal getAnimal();

}

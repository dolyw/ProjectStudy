package com.design.abstractfactory.factory;

import com.design.abstractfactory.product.Animal;
import com.design.abstractfactory.product.Plant;

/**
 * 抽象工厂-一个工厂可以生产一族产品
 * 抽象农场-一个农场可以生产动物和植物
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 11:42
 */
public interface AbstractFarmFactory {

    /**
     * 获取动物
     *
     * @param
     * @return com.design.abstractfactory.product.Animal
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/28 11:42
     */
    Animal getAnimal();

    /**
     * 获取植物
     *
     * @param
     * @return com.design.abstractfactory.product.Plant
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/28 15:48
     */
    Plant getPlant();

}

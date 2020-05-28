package com.design.factorymethod.factory;

import com.design.factorymethod.product.Animal;
import com.design.factorymethod.product.impl.CattleImpl;
import com.design.factorymethod.product.impl.HorseImpl;

/**
 * 简单工厂(静态工厂)
 *
 * 非常确定系统只需要一个具体工厂类，那么不妨把抽象工厂类合并到具体工厂类中去
 * 由于只有一个具体工厂类，所以将工厂方法改为静态方法获取产品
 *
 * 简单工厂模式实现了生成产品类的代码跟客户端代码分离，
 * 在工厂类中你可以添加所需的生成产品的逻辑代码，但是问题来了，
 * 优秀的Java代码是符合“开放-封闭”原则的，也就是说对扩展开发，对修改关闭，
 * 如果你要加一个新的产品，你就要修改工厂类里面的生成产品的代码，在这里你就要增加if-else判断。
 * 对于这个问题，使用工厂方法模式就可以解决这个问题
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/28 11:28
 */
public class SimpleStaticFactory {

    /**
     * 牛类
     */
    public static final String CATTLE = "Cattle";

    /**
     * 马类
     */
    public static final String HORSE = "Horse";

    /**
     * 获取产品
     *
     * @param animalType
     * @return com.design.factorymethod.product.Animal
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/28 11:33
     */
    public static Animal getAnimal(String animalType) {
        if (animalType != null) {
            if (CATTLE.equalsIgnoreCase(animalType)) {
                return new CattleImpl();
            } else if (HORSE.equalsIgnoreCase(animalType)) {
                return new HorseImpl();
            }
        }
        return null;
    }

}

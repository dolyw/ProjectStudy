package com.design.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/24 14:15
 */
public class FlyweightFactory {

    private Map<String, Flyweight> flyweightMap = new HashMap<>(16);

    /**
     * 工厂根据key获取Flyweight
     *
     * @param key
     * @return com.design.flyweight.Flyweight
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2022/1/24 14:19
     */
    public Flyweight getFlyweight(String key) {
        Flyweight flyweight = flyweightMap.get(key);
        if (flyweight == null) {
            flyweight  = new ConcreteFlyweight(key);
            flyweightMap.put(key, flyweight);
            System.out.println(key + "不存在，进行创建");
        } else {
            System.out.println(key + "存在，成功获取");
        }
        return flyweight;
    }

}

package com.design.flyweight;

/**
 * 具体享元
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/24 14:12
 */
public class ConcreteFlyweight implements Flyweight {

    private String key;

    public ConcreteFlyweight(String key) {
        this.key = key;
    }

    @Override
    public void handle(UnsharableFlyweight unsharableFlyweight) {
        System.out.println(this.key + "被调用，非享元信息:" + unsharableFlyweight.getMsg());
    }
}

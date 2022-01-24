package com.design.flyweight;

/**
 * 抽象享元
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/24 14:12
 */
public interface Flyweight {

    /**
     * 处理方法
     *
     * @param unsharableFlyweight
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2022/1/24 14:15
     */
    void handle(UnsharableFlyweight unsharableFlyweight);

}

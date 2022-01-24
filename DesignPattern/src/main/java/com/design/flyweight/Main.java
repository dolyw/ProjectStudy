package com.design.flyweight;

/**
 * 享元模式
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/24 13:44
 */
public class Main {

    public static void main(String[] args) {
        // 创建工厂
        FlyweightFactory flyweightFactory = new FlyweightFactory();
        // 使用工厂创建3个apple，2个banana对象
        Flyweight apple1 = flyweightFactory.getFlyweight("apple");
        Flyweight apple2 = flyweightFactory.getFlyweight("apple");
        Flyweight apple3 = flyweightFactory.getFlyweight("apple");
        Flyweight banana1 = flyweightFactory.getFlyweight("banana");
        Flyweight banana2 = flyweightFactory.getFlyweight("banana");
        // 使用不同的对象处理发现使用的其实是同一个
        apple1.handle(new UnsharableFlyweight("第1次处理apple"));
        apple2.handle(new UnsharableFlyweight("第2次处理apple"));
        apple3.handle(new UnsharableFlyweight("第3次处理apple"));
        banana1.handle(new UnsharableFlyweight("第1次处理banana"));
        banana2.handle(new UnsharableFlyweight("第2次处理banana"));
    }

}

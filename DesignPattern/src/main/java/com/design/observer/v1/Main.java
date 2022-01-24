package com.design.observer.v1;

import com.design.observer.v1.observer.SubscribeConcreteObserver;
import com.design.observer.v1.subject.BlogConcreteSubject;

/**
 * 观察者模式，使用JDK的提供的类完成
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/20 14:02
 */
public class Main {

    public static void main(String[] args) {
        // 构建具体目标
        BlogConcreteSubject blogConcreteSubject = new BlogConcreteSubject("dolyw.com");
        // 添加观察者
        blogConcreteSubject.addObserver(new SubscribeConcreteObserver("王明"));
        blogConcreteSubject.addObserver(new SubscribeConcreteObserver("王美丽"));
        // 具体目标发送变化，发布新博客
        blogConcreteSubject.publish("母猪的产后护理");
    }

}

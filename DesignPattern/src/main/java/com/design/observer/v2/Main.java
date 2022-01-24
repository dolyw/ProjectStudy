package com.design.observer.v2;

import com.design.observer.v2.observer.SubscribeConcreteObserver;
import com.design.observer.v2.subject.AbstractSubject;
import com.design.observer.v2.subject.BlogConcreteSubject;

/**
 * 观察者模式，使用原生方式完成
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/20 14:02
 */
public class Main {

    public static void main(String[] args) {
        // 构建具体目标
        AbstractSubject blogConcreteSubject = new BlogConcreteSubject("dolyw.com");
        // 添加观察者
        blogConcreteSubject.addObserver(new SubscribeConcreteObserver("王明"));
        blogConcreteSubject.addObserver(new SubscribeConcreteObserver("王美丽"));
        // 具体目标发送变化，发布新博客
        blogConcreteSubject.publish("Java入门到放弃");
    }

}

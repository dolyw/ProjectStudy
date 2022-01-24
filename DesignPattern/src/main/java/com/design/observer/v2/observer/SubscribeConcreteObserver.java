package com.design.observer.v2.observer;

import com.design.observer.v2.subject.AbstractSubject;
import com.design.observer.v2.subject.BlogConcreteSubject;

/**
 * 具体观察者，使用原生方式实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/20 14:21
 */
public class SubscribeConcreteObserver extends AbstractObserver {

    private String name;

    public SubscribeConcreteObserver(String name) {
        this.name = name;
    }

    /**
     * 订阅目标发布新博客后，订阅者接收到消息
     *
     * @param abstractSubject
     * @param arg
     */
    @Override
    public void update(AbstractSubject abstractSubject, Object arg) {
        BlogConcreteSubject blog = (BlogConcreteSubject) abstractSubject;
        String title = (String) arg;
        System.out.println(name + "订阅的博客[" + blog.getName() + "]又发布了新文章-" + title);
    }
}

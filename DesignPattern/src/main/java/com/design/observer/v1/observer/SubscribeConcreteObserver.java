package com.design.observer.v1.observer;

import com.design.observer.v1.subject.BlogConcreteSubject;

import java.util.Observable;
import java.util.Observer;

/**
 * 具体观察者，使用JDK提供的Observable类抽象观察者Observer
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/20 14:21
 */
public class SubscribeConcreteObserver implements Observer {

    private String name;

    public SubscribeConcreteObserver(String name) {
        this.name = name;
    }

    /**
     * 发布新博客后，订阅者接收到消息
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        BlogConcreteSubject blog = (BlogConcreteSubject) o;
        String title = (String) arg;
        System.out.println(name + "订阅的博客[" + blog.getName() + "]又发布了新文章-" + title);
    }
}

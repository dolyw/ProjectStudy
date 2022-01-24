package com.design.observer.v1.subject;

import java.util.Observable;

/**
 * 具体目标，使用JDK提供的Observable类抽象目标
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/20 14:13
 */
public class BlogConcreteSubject extends Observable {

    private String name;

    public BlogConcreteSubject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 发布新文章，通知所有订阅了博客的人
     *
     * @param title
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2022/1/20 14:17
     */
    public void publish(String title) {
        // 设置标识位 changed = true，表示被观察者发生了改变
        setChanged();
        // 通知观察者，可以给观察者传递数据
        notifyObservers(title);
    }

}

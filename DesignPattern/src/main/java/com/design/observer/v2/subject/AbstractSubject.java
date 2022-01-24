package com.design.observer.v2.subject;

import com.design.observer.v2.observer.AbstractObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象目标
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/20 14:03
 */
public abstract class AbstractSubject {

    protected List<AbstractObserver> observerList = new ArrayList<>();

    public synchronized void addObserver(AbstractObserver observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (!observerList.contains(observer)) {
            observerList.add(observer);
        }
    }

    public synchronized void deleteObserver(AbstractObserver observer) {
        observerList.remove(observer);
    }

    /**
     * 通知所有观察者
     *
     * @param arg
     */
    public void notifyObservers(Object arg) {
        for (AbstractObserver abstractObserver : observerList) {
            abstractObserver.update(this, arg);
        }
    }

    /**
     * 目标发生变化
     *
     * @param title
     */
    public abstract void publish(String title);

}

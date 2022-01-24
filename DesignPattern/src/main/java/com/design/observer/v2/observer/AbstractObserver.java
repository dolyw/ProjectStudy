package com.design.observer.v2.observer;

import com.design.observer.v2.subject.AbstractSubject;

/**
 * 抽象观察者
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/20 14:05
 */
public abstract class AbstractObserver {

    /**
     * 观察者接收通知
     *
     * @param abstractSubject
	 * @param arg
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2022/1/21 15:39
     */
    public abstract void update(AbstractSubject abstractSubject, Object arg);

}

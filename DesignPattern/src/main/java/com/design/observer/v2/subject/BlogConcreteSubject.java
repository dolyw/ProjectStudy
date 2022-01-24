package com.design.observer.v2.subject;

/**
 * 具体目标，使用原生方式实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/20 14:13
 */
public class BlogConcreteSubject extends AbstractSubject {

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
    @Override
    public void publish(String title) {
        // 通知观察者，可以给观察者传递数据
        notifyObservers(title);
    }

}

package com.design.visitor;

/**
 * 计算机抽象节点
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/3 17:33
 */
public abstract class ComputerNode {

    public abstract void accept(Visitor visitor);

    /**
     * 原始价格
     * @return
     */
    public double getPrice() {
        return 0;
    }

}

package com.design.visitor;

/**
 * 计算机内存条具体节点
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/3 17:33
 */
public class MemoryComputerNodeImpl extends ComputerNode {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public double getPrice() {
        return 500;
    }
}

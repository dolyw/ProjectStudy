package com.design.visitor;

/**
 * 计算机结构对象，也可以同时充当具体节点的作用
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/3 17:33
 */
public class ComputerStructure extends ComputerNode {

    /*ComputerNode computerNode = new CpuComputerNodeImpl();
    ComputerNode memoryComputerNode = new MemoryComputerNodeImpl();
    ComputerNode boardComputerNode = new BoardComputerNodeImpl();*/

    protected ComputerNode[] computerNodeArray;

    public ComputerStructure() {
        computerNodeArray = new ComputerNode[] {new CpuComputerNodeImpl(),
                new MemoryComputerNodeImpl(), new BoardComputerNodeImpl()};
    }

    @Override
    public void accept(Visitor visitor) {
        /*this.computerNode.accept(visitor);
        this.memoryComputerNode.accept(visitor);
        this.boardComputerNode.accept(visitor);*/
        for (ComputerNode computerNode : computerNodeArray) {
            computerNode.accept(visitor);
        }
        // 整个结构对象，也同时充当节点的作用就需要调用
        visitor.visit(this);
    }

    @Override
    public double getPrice() {
        // 计算机除去CPU内存主板后其他东西的总和
        return 1000;
    }
}

package com.design.visitor;

/**
 * 公司访问者，CPU 85折，内存7折，主板8折，其他6折
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/3 17:33
 */
public class CorpVisitorImpl implements Visitor {

    private double totalPrice = 0.0;

    @Override
    public void visit(CpuComputerNodeImpl cpuComputerNode) {
        totalPrice = totalPrice + cpuComputerNode.getPrice() * 0.85;
    }

    @Override
    public void visit(MemoryComputerNodeImpl memoryComputerNode) {
        totalPrice = totalPrice + memoryComputerNode.getPrice() * 0.7;
    }

    @Override
    public void visit(BoardComputerNodeImpl boardComputerNode) {
        totalPrice = totalPrice + boardComputerNode.getPrice() * 0.8;
    }

    /**
     * 总结点本身触发
     *
     * @param computerStructure
     */
    public void visit(ComputerStructure computerStructure) {
        totalPrice = totalPrice * 0.6;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

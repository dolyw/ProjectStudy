package com.design.visitor;

/**
 * 个人访问者，CPU 95折，内存8折，主板9折，其他8折
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/3 17:33
 */
public class PersonelVisitorImpl implements Visitor {

    private double totalPrice = 0.0;

    @Override
    public void visit(CpuComputerNodeImpl cpuComputerNode) {
        totalPrice = totalPrice + cpuComputerNode.getPrice() * 0.95;
    }

    @Override
    public void visit(MemoryComputerNodeImpl memoryComputerNode) {
        totalPrice = totalPrice + memoryComputerNode.getPrice() * 0.8;
    }

    @Override
    public void visit(BoardComputerNodeImpl boardComputerNode) {
        totalPrice = totalPrice + boardComputerNode.getPrice()* 0.9;
    }

    /**
     * 计算机除去CPU内存主板后其他东西的总和
     *
     * @param computerStructure
     */
    public void visit(ComputerStructure computerStructure) {
        totalPrice = totalPrice * 0.8;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

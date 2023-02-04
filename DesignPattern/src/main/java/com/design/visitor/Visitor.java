package com.design.visitor;

/**
 * 访问者接口
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/3 17:33
 */
public interface Visitor {

    void visit(CpuComputerNodeImpl cpuComputerNode);

    void visit(MemoryComputerNodeImpl memoryComputerNode);

    void visit(BoardComputerNodeImpl boardComputerNode);

    void visit(ComputerStructure computerStructure);
}

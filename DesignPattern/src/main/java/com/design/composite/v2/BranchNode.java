package com.design.composite.v2;

import java.util.ArrayList;
import java.util.List;

/**
 * 分支节点
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 14:15
 */
class BranchNode extends Node {
    List<Node> nodes = new ArrayList<>();

    String name;

    public BranchNode(String name) {
        this.name = name;
    }

    @Override
    public void p() {
        System.out.println(name);
    }

    public void add(Node n) {
        nodes.add(n);
    }
}

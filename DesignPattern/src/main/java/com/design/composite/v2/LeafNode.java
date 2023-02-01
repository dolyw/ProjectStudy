package com.design.composite.v2;

/**
 * 叶子节点
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 14:15
 */
public class LeafNode extends Node {

    String content;

    public LeafNode(String content) {
        this.content = content;
    }

    @Override
    public void p() {
        System.out.println(content);
    }

}

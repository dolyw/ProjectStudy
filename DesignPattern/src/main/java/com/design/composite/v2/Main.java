package com.design.composite.v2;

/**
 * 组合模式
 *
 * 模拟树形层次结构输出
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 14:07
 */
public class Main {

    public static void main(String[] args) {

        BranchNode root = new BranchNode("章节");
        BranchNode chapter1 = new BranchNode("章节1");
        BranchNode chapter2 = new BranchNode("章节2");
        Node r1 = new LeafNode("尾声");
        Node c11 = new LeafNode("小结1-1");
        Node c12 = new LeafNode("小结1-2");
        BranchNode b21 = new BranchNode("选读1");
        Node c211 = new LeafNode("选读1小结1");
        Node c212 = new LeafNode("选读1小结2");

        root.add(chapter1);
        root.add(chapter2);
        root.add(r1);
        chapter1.add(c11);
        chapter1.add(c12);
        chapter2.add(b21);
        b21.add(c211);
        b21.add(c212);

        tree(root, 0);

    }

    public static void tree(Node b, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("--");
        }

        b.p();

        if (b instanceof BranchNode) {
            for (Node n : ((BranchNode) b).nodes) {
                tree(n, depth + 1);
            }
        }
    }

}

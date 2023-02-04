package com.design.visitor;

/**
 * 访问者模式，一个购买组装电脑各种零件，对个人及公司访问者不同优惠的例子
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/3 17:33
 */
public class Main {

    public static void main(String[] args) {
        // 整个结构对象，也同时充当节点的作用
        ComputerStructure computerStructure = new ComputerStructure();
        // 个人购买
        PersonelVisitorImpl personelVisitor = new PersonelVisitorImpl();
        computerStructure.accept(personelVisitor);
        System.out.println(personelVisitor.getTotalPrice());
        // 公司购买
        CorpVisitorImpl corpVisitor = new CorpVisitorImpl();
        computerStructure.accept(corpVisitor);
        System.out.println(corpVisitor.getTotalPrice());
    }

}

package com.design.adapter;

/**
 * 适配器模式是作为两个不兼容的接口之间的桥梁
 * 将一个类的接口转换为期望的另一个接口，使原本不兼容的类可以一起工作
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/1 10:37
 */
public class Main {

    public static void main(String[] args) {
        // 商店购买只能使用人民币
        Shop shop = new Shop();
        Rmb rmb = new Rmb(100);
        // 购买成功
        shop.buy(rmb);
        System.out.println("------------");
        // 现在有美元无法购买
        Dollar dollar = new Dollar(10);
        // shop.buy(dollar);
        // 使用银行适配器完成
        BankAdapter bankAdapter = new BankAdapter();
        shop.buy(bankAdapter.changeRmbMoney(dollar));
    }

}

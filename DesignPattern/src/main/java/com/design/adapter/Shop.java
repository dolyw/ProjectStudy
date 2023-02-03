package com.design.adapter;

/**
 * 商店
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/1 11:08
 */
public class Shop {

    public void buy(Rmb rmb) {
        System.out.println("购买成功，消费" + rmb.getAmount() + "元人民币");
    }

}

package com.design.adapter;

/**
 * 银行适配器
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/1 10:40
 */
public class BankAdapter {

    public Rmb changeRmbMoney(Dollar dollar) {
        return new Rmb(dollar.getAmount() * 7);
    }

}

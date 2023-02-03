package com.design.bridge;

/**
 * 黑色
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 17:05
 */
public class BlackColorImpl implements Color {
    @Override
    public void bepaint(String shape) {
        System.out.println("黑色的" + shape);
    }
}

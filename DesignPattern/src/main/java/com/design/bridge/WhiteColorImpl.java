package com.design.bridge;

/**
 * 白色
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 17:05
 */
public class WhiteColorImpl implements Color {
    @Override
    public void bepaint(String shape) {
        System.out.println("白色的" + shape);
    }
}

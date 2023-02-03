package com.design.bridge;

/**
 * 灰色
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 17:05
 */
public class GrayColorImpl implements Color {
    @Override
    public void bepaint(String shape) {
        System.out.println("灰色的" + shape);
    }
}

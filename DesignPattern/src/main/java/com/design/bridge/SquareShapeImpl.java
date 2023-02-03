package com.design.bridge;

/**
 * 正方形
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 17:05
 */
public class SquareShapeImpl extends Shape {
    @Override
    public void draw() {
        color.bepaint("正方形");
    }
}

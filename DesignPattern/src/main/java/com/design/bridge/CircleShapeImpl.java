package com.design.bridge;

/**
 * 圆形
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 17:05
 */
public class CircleShapeImpl extends Shape {
    @Override
    public void draw() {
        color.bepaint("圆形");
    }
}

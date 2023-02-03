package com.design.bridge;

/**
 * 形状抽象类
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 17:05
 */
public abstract class Shape {
    protected Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void draw();
}

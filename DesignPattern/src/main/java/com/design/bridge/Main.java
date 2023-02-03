package com.design.bridge;

/**
 * 桥接模式 - 在有多种可能会变化的情况下，用继承会造成类爆炸问题，扩展起来不灵活
 * 使用一个画不同颜色的形状的小例子说明，比如可以画正方形、长方形、圆形
 * 但是现在我们需要给这些形状进行上色，这里有三种颜色：白色、灰色、黑色
 * 这里我们可以画出 3 * 3 = 9 种图形，一般直接的做法是使用继承，
 * 为每种形状都提供各种颜色的版本，这样的做法不灵活，
 * 会导致实现类几何增长，所以引出了桥接模式，根据实际需要对颜色和形状进行组合
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 17:05
 */
public class Main {

    public static void main(String[] args) {
        // 颜色
        Color whiteColor = new WhiteColorImpl();
        Color blackColor = new BlackColorImpl();
        Color grayColor = new GrayColorImpl();
        // 形状
        Shape square = new SquareShapeImpl();
        Shape rectangleShape = new RectangleShapeImpl();
        Shape circleShape = new CircleShapeImpl();
        // 白色的正方形
        square.setColor(whiteColor);
        square.draw();
        // 灰色的正方形
        square.setColor(grayColor);
        square.draw();
        // 黑色的长方形
        rectangleShape.setColor(blackColor);
        rectangleShape.draw();
        // 灰色的圆形
        circleShape.setColor(grayColor);
        circleShape.draw();
        // 白色的圆形
        circleShape.setColor(whiteColor);
        circleShape.draw();
    }

}

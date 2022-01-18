package com.design.decorator.component;

/**
 * 炒面具体构件
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/18 16:30
 */
public class FryNoodleConcreteComponent implements NoodleComponent {

    @Override
    public String remark() {
        return "炒面10快";
    }

    @Override
    public Integer price() {
        return 10;
    }
}

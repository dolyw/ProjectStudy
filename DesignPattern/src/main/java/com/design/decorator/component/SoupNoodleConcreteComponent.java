package com.design.decorator.component;

/**
 * 汤面具体构件
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/18 16:30
 */
public class SoupNoodleConcreteComponent implements NoodleComponent {

    @Override
    public String remark() {
        return "汤面8快";
    }

    @Override
    public Integer price() {
        return 8;
    }
}

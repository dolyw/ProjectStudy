package com.design.decorator.decorator;

import com.design.decorator.component.NoodleComponent;

/**
 * 煎蛋具体装饰
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/18 16:44
 */
public class FriedEggConcreteDecorator extends AbstractSideDishDecorator {

    public FriedEggConcreteDecorator(NoodleComponent noodleComponent) {
        super(noodleComponent);
    }

    @Override
    public String remark() {
        return super.remark() + " 加份煎蛋3快";
    }

    @Override
    public Integer price() {
        return super.price() + 3;
    }
}

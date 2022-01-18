package com.design.decorator.decorator;

import com.design.decorator.component.NoodleComponent;

/**
 * 卤蛋具体装饰
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/18 16:44
 */
public class MarinatedEggConcreteDecorator extends AbstractSideDishDecorator {

    public MarinatedEggConcreteDecorator(NoodleComponent noodleComponent) {
        super(noodleComponent);
    }

    @Override
    public String remark() {
        return super.remark() + " 加份卤蛋2快";
    }

    @Override
    public Integer price() {
        return super.price() + 2;
    }
}

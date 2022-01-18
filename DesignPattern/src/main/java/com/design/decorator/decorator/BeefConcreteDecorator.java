package com.design.decorator.decorator;

import com.design.decorator.component.NoodleComponent;

/**
 * 牛肉具体装饰
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/18 16:44
 */
public class BeefConcreteDecorator extends AbstractSideDishDecorator {

    public BeefConcreteDecorator(NoodleComponent noodleComponent) {
        super(noodleComponent);
    }

    @Override
    public String remark() {
        return super.remark() + " 加份牛肉5快";
    }

    @Override
    public Integer price() {
        return super.price() + 5;
    }
}

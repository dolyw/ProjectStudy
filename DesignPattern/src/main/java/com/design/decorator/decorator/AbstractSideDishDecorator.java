package com.design.decorator.decorator;

import com.design.decorator.component.NoodleComponent;

/**
 * 配菜抽象装饰
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/18 16:40
 */
public abstract class AbstractSideDishDecorator implements NoodleComponent {

    private NoodleComponent noodleComponent;

    public AbstractSideDishDecorator(NoodleComponent noodleComponent) {
        this.noodleComponent = noodleComponent;
    }

    @Override
    public String remark() {
        return noodleComponent.remark();
    }

    @Override
    public Integer price() {
        return noodleComponent.price();
    }
}

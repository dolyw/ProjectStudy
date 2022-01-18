package com.design.decorator;

import com.design.decorator.component.FryNoodleConcreteComponent;
import com.design.decorator.component.NoodleComponent;
import com.design.decorator.component.SoupNoodleConcreteComponent;
import com.design.decorator.decorator.BeefConcreteDecorator;
import com.design.decorator.decorator.FriedEggConcreteDecorator;
import com.design.decorator.decorator.MarinatedEggConcreteDecorator;

/**
 * 装饰器模式
 * 通过不同的装饰器自由组合
 * 我们可以灵活的组装出各式各样的面加配菜
 * 这正是装饰者模式的优点
 * 但明显可以看出代码变复杂了
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/18 16:05
 */
public class Main {

    public static void main(String[] args) {
        // 汤面
        NoodleComponent soupNoodleConcreteComponent = new SoupNoodleConcreteComponent();
        // 加牛肉
        soupNoodleConcreteComponent = new BeefConcreteDecorator(soupNoodleConcreteComponent);
        // 加煎蛋
        soupNoodleConcreteComponent = new FriedEggConcreteDecorator(soupNoodleConcreteComponent);
        System.out.println(soupNoodleConcreteComponent.remark());
        System.out.println(soupNoodleConcreteComponent.price());
        System.out.println("----------------------");
        // 炒面
        NoodleComponent fryNoodleConcreteComponent = new FryNoodleConcreteComponent();
        // 加牛肉
        fryNoodleConcreteComponent = new BeefConcreteDecorator(fryNoodleConcreteComponent);
        // 加卤蛋
        fryNoodleConcreteComponent = new MarinatedEggConcreteDecorator(fryNoodleConcreteComponent);
        System.out.println(fryNoodleConcreteComponent.remark());
        System.out.println(fryNoodleConcreteComponent.price());
        System.out.println("-----------------------");
        // 炒面
        NoodleComponent fryNoodleConcreteComponentEasy = new FryNoodleConcreteComponent();
        // 加牛肉加卤蛋加煎蛋
        fryNoodleConcreteComponentEasy =
                new BeefConcreteDecorator(
                        new MarinatedEggConcreteDecorator(new FriedEggConcreteDecorator(fryNoodleConcreteComponentEasy)));
        System.out.println(fryNoodleConcreteComponentEasy.remark());
        System.out.println(fryNoodleConcreteComponentEasy.price());
    }

}

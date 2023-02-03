package com.design.interpreter;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 解释器模式 - 可利用场景比较少
 * 可以直接使用Spring及Java自带的
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 16:22
 */
public class Main {

    public static void main(String[] args) {
        // 创建一个ExpressionParser对象，用于解析表达式
        ExpressionParser parser = new SpelExpressionParser();
        // 最简单的字符串表达式
        Expression exp = parser.parseExpression("'HelloWorld'");
        System.out.println("'HelloWorld'的结果：" + exp.getValue());
        // 调用方法的表达式
        exp = parser.parseExpression("'HelloWorld'.concat('!')");
        System.out.println("'HelloWorld'.concat('!')的结果： " + exp.getValue());
        // 调用对象的getter方法
        exp = parser.parseExpression("'HelloWorld'.bytes");
        System.out.println("'HelloWorld'.bytes的结果： " + exp.getValue());
        // 访问对象的属性(相当于HelloWorld.getBytes().length)
        exp = parser.parseExpression("'HelloWorld'.bytes.length");
        System.out.println("'HelloWorld'.bytes.length的结果：" + exp.getValue());
        // 使用构造器来创建对象
        exp = parser.parseExpression("new String('helloworld')" + ".toUpperCase()");
        System.out.println("new String('helloworld')" + ".toUpperCase()的结果是： " + exp.getValue(String.class));
        // List解释
        List<Boolean> list = new ArrayList<>();
        list.add(true);
        EvaluationContext ctx2 = new StandardEvaluationContext();
        // 将list设置成EvaluationContext的一个变量
        ctx2.setVariable("list", list);
        // 修改list变量的第一个元素的值
        parser.parseExpression("#list[0]").setValue(ctx2, "false");
        // list集合的第一个元素被改变
        System.out.println("list集合的第一个元素为：" + parser.parseExpression("#list[0]").getValue(ctx2));
    }

}

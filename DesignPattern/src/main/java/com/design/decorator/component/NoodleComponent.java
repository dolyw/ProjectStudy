package com.design.decorator.component;

/**
 * 面条抽象构件
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/18 16:30
 */
public interface NoodleComponent {

    /**
     * 备注
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2022/1/18 16:31
     */
    String remark();

    /**
     * 价格
     *
     * @param
     * @return java.lang.Integer
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2022/1/18 16:31
     */
    Integer price();

}

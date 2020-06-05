package com.design.facade.v2;

/**
 * Facade - 外观(门面)
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 10:30
 */
public class Facade {

    private SubSystem1 subSystem1 = new SubSystem1();
    private SubSystem2 subSystem2 = new SubSystem2();
    private SubSystem3 subSystem3 = new SubSystem3();

    public void toDo1() {
        subSystem1.toDo();
    }

    public void toDo2() {
        subSystem2.toDo();
    }

    public void toDo3() {
        subSystem3.toDo();
    }
}

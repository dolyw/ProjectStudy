package com.design.chainofresponsibility;

import com.design.chainofresponsibility.handler.AbstractLeaderHandle;
import com.design.chainofresponsibility.handler.ClassAdviserHandle;
import com.design.chainofresponsibility.handler.DeanHandle;
import com.design.chainofresponsibility.handler.DepartmentHeadHandle;

/**
 * 学生客户端
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/19 13:46
 */
public class StudentClient {

    AbstractLeaderHandle abstractLeaderHandle;

    /**
     * 构造方法初始化责任链
     */
    public StudentClient() {
        AbstractLeaderHandle classAdviserHandle = new ClassAdviserHandle();
        AbstractLeaderHandle departmentHeadHandle = new DepartmentHeadHandle();
        AbstractLeaderHandle deanHandle = new DeanHandle();
        classAdviserHandle.setNext(departmentHeadHandle);
        departmentHeadHandle.setNext(deanHandle);
        abstractLeaderHandle = classAdviserHandle;
    }

    /**
     * 请假方法
     *
     * @param day
	 * @param remark
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2022/1/19 13:52
     */
    public void leave(Integer day, String remark) {
        abstractLeaderHandle.handleRequest(day, remark);
    }

}

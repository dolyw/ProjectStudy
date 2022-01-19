package com.design.chainofresponsibility.handler;

/**
 * 班主任具体处理者Concrete Handler
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/19 11:06
 */
public class ClassAdviserHandle extends AbstractLeaderHandle {

    /**
     * 处理请求的方法
     *
     * @param day
	 * @param remark
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2022/1/19 11:21
     */
    @Override
    public void handleRequest(Integer day, String remark) {
        if (day <= 3) {
            System.out.println("班主任批准了你的假期" + day + "天，备注：" + remark);
        } else {
            this.handleNext(this.getNext(), day, remark);
        }
    }
}

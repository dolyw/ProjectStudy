package com.design.chainofresponsibility.handler;

/**
 * 抽象处理者Handle
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/1/19 11:06
 */
public abstract class AbstractLeaderHandle {

    AbstractLeaderHandle next;

    public AbstractLeaderHandle getNext() {
        return next;
    }

    public void setNext(AbstractLeaderHandle next) {
        this.next = next;
    }

    /**
     * 处理请求的方法
     *
     * @param day
     * @param remark
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2022/1/19 11:08
     */
    public abstract void handleRequest(Integer day, String remark);

    /**
     * 上级处理
     *
     * @param next
	 * @param day
	 * @param remark
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2022/1/19 11:33
     */
    public void handleNext(AbstractLeaderHandle next, Integer day, String remark) {
        if (next != null) {
            next.handleRequest(day, remark);
        } else {
            System.out.println("请假天数太多，没人批准，备注：" + remark);
        }
    }
}

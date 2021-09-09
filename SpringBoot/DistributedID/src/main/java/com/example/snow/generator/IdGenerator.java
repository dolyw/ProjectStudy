package com.example.snow.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ID生成器
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/9/2 16:50
 */
@Component
public class IdGenerator {

    @Autowired
    private IdWorkerUpdate idWorkerUpdate;

    /**
     * OrderCode生成
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/9/2 16:51
     */
    public String nextOrderCode() {
        return IdSegmentEnum.ORDER_CODE.getSegment() + idWorkerUpdate.nextId();
    }

    /**
     * UserId生成
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/9/2 16:51
     */
    public String nextUserId() {
        return IdSegmentEnum.USER_ID.getSegment() + idWorkerUpdate.nextId();
    }
}

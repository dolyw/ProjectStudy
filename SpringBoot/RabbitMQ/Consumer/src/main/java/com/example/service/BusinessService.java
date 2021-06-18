package com.example.service;

import org.springframework.stereotype.Service;

/**
 * 业务服务
 *
 * @author wliduo[i@dolyw.com]
 * @date 2021/6/18 15:15
 */
@Service
public class BusinessService {

    /**
     * 处理业务方法
     *
     * @param msg
     * @return boolean
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2021/6/18 15:16
     */
    public boolean handle(String msg) {
        System.out.println("BusinessService: " + msg);
        return Boolean.TRUE;
    }

}

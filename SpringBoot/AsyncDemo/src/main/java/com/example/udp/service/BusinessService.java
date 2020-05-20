package com.example.udp.service;

/**
 * BusinessService
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/20 11:57
 */
public interface BusinessService {

    /**
     * UDP消息处理方法
     *
     * @param message
     * @return void
     * @throws Exception e
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/20 11:57
     */
    void udpHandleMethod(String message) throws Exception;

}

package com.example.service;

import java.util.concurrent.Future;

/**
 * AsyncService
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/19 14:18
 */
public interface AsyncService {

    /**
     * 任务1
     *
     * @param
     * @return void
     * @throws Exception e
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/19 14:45
     */
    void task1() throws Exception;

    /**
     * 任务2
     *
     * @param
     * @return java.util.concurrent.Future<java.lang.String>
     * @throws Exception e
     * @author wliduo[i@dolyw.com]
     * @date 2020/5/20 10:04
     */
    Future<String> task2() throws Exception;

}

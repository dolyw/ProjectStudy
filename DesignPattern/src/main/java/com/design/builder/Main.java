package com.design.builder;

import java.util.Date;

/**
 * 建造者模式
 *
 * @author wliduo[i@dolyw.com]
 * @date 2022/2/11 14:27
 */
public class Main {

    public static void main(String[] args) {
        App app = App.Builder()
                .withAppId(1L)
                .withAppCode("App")
                .withAppName("XX系统")
                .withCreatedBy("XXX")
                .build();
        Channel channel = Channel.builder()
                .channelId(1L)
                .channelName("XXX")
                .channelType(1)
                .createdTime(new Date())
                .build();
    }
}

package com.design.proxy;

/**
 * 实现媒体接口 - 音乐媒体
 */
public class MusicMediaImpl implements Media {

    @Override
    public void play(String file) {
        System.out.println("Play Music: " + file);
    }

}

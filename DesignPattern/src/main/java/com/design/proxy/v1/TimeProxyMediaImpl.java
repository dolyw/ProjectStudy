package com.design.proxy.v1;

import com.design.proxy.Media;

/**
 * 静态代理二
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 16:43
 */
public class TimeProxyMediaImpl implements Media {

    Media media;

    public TimeProxyMediaImpl(Media media) {
        this.media = media;
    }

    @Override
    public void play(String file) {
        System.out.println(System.currentTimeMillis());
        media.play(file);
        System.out.println(System.currentTimeMillis());
    }

}

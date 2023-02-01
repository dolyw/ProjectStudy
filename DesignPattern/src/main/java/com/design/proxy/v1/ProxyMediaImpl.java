package com.design.proxy.v1;

import com.design.proxy.Media;

/**
 * 静态代理一
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 16:43
 */
public class ProxyMediaImpl implements Media {

    Media media;

    public ProxyMediaImpl(Media media) {
        this.media = media;
    }

    @Override
    public void play(String file) {
        System.out.println("Start");
        media.play(file);
        System.out.println("End");
    }

}

package com.design.proxy.v1;

import com.design.proxy.Media;
import com.design.proxy.MusicMediaImpl;

/**
 * 静态代理事先知道要代理的是什么，目标角色固定不灵活
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 16:41
 */
public class Main {

    public static void main(String[] args) {
        // 组合嵌套多个代理类
        Media musicMedia = new MusicMediaImpl();
        // musicMedia.play("音乐");
        Media proxyMedia = new ProxyMediaImpl(musicMedia);
        // proxyMedia.play("音乐");
        Media timeProxyMedia = new TimeProxyMediaImpl(proxyMedia);
        timeProxyMedia.play("音乐");
    }

}

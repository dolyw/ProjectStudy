package com.design.proxy.v2;

import com.design.proxy.Media;
import com.design.proxy.MovieMedia;
import com.design.proxy.MusicMediaImpl;

/**
 * 相比于静态代理，动态代理在创建代理对象上更加的灵活
 *
 * 验证一，音乐媒体类实现接口都可以成功代理
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 16:55
 */
public class Main {

    public static void main(String[] args) {
        Media musicMedia = new MusicMediaImpl();
        // musicMedia.play("音乐");
        // 使用Jdk动态代理接口实现方式
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(musicMedia);
        Media jdkDynamicProxyMedia = (Media) jdkDynamicProxy.proxy();
        jdkDynamicProxyMedia.play("Jdk音乐");

        System.out.println("----------");

        // 使用Cglib动态代理接口实现方式
        CglibDynamicProxy cglibDynamicProxy = new CglibDynamicProxy(musicMedia);
        Media cglibDynamicProxyMedia = (Media) cglibDynamicProxy.proxy();
        cglibDynamicProxyMedia.play("Cglib音乐");
    }

}

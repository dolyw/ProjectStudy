package com.design.proxy.v2;

import com.design.proxy.MovieMedia;

/**
 * 相比于静态代理，动态代理在创建代理对象上更加的灵活
 *
 * 验证二，电影媒体类未实现接口只有Cglib可以代理
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 16:55
 */
public class Main2 {

    public static void main(String[] args) {
        MovieMedia movieMedia = new MovieMedia();
        // movieMedia.play("电影");
        // 使用Cglib动态代理Class类
        CglibDynamicProxy cglibDynamicProxy = new CglibDynamicProxy(movieMedia);
        MovieMedia cglibDynamicProxyMedia = (MovieMedia) cglibDynamicProxy.proxy();
        cglibDynamicProxyMedia.play("Cglib电影");

        System.out.println("----------");

        // 使用Jdk动态代理Class类，运行失败，Jdk必须实现接口才可以代理
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(movieMedia);
        MovieMedia jdkDynamicProxyMedia = (MovieMedia) jdkDynamicProxy.proxy();
        jdkDynamicProxyMedia.play("Jdk电影");
    }

}

package com.design.proxy.v2;

import com.design.proxy.MovieMedia;
import com.design.proxy.MusicMediaImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK代理是不需要依赖第三方的库，只要JDK环境就可以进行代理，需要满足以下要求
 * 1. 实现InvocationHandler接口，重写invoke()
 * 2. 使用Proxy.newProxyInstance()产生代理对象
 * 3. 被代理的对象必须要实现接口
 *
 * JDK动态代理机制是委托机制，只能对实现接口的类生成代理，通过反射动态实现接口类
 * JDK代理使用的是反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理
 * JDK创建代理对象效率较高，执行效率较低
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 16:52
 */
public class JdkDynamicProxy implements InvocationHandler {

    /**
     * 代理的目标对象
     */
    private Object object;

    public JdkDynamicProxy(Object object) {
        this.object = object;
    }

    public Object proxy() {
        Class<?> clazz = object.getClass();
        // 生成代理对象
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeMethod(object);
        Object result = method.invoke(object, args);
        afterMethod(object);
        return result;
    }

    private void beforeMethod(Object object) {
        if (object instanceof MusicMediaImpl) {
            System.out.println("MusicMediaImpl Start");
        } else if (object instanceof MovieMedia) {
            System.out.println("MovieMedia Start");
        } else {
            throw new RuntimeException("暂不支持代理" + object.getClass() + "类型");
        }
    }

    private void afterMethod(Object object) {
        if (object instanceof MusicMediaImpl) {
            System.out.println("MusicMediaImpl End");
        } else if (object instanceof MovieMedia) {
            System.out.println("MovieMedia End");
        } else {
            throw new RuntimeException("暂不支持代理" + object.getClass() + "类型");
        }
    }
}

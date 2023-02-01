package com.design.proxy.v2;

import com.design.proxy.MovieMedia;
import com.design.proxy.MusicMediaImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLib必须依赖于CGLib的类库，需要满足以下要求
 * 1. 实现MethodInterceptor接口，重写intercept()
 * 2. 使用Enhancer对象.create()产生代理对象
 * 3. 因为是继承机制，不能代理final修饰的类
 *
 * CGLIB则使用的继承机制，针对类实现代理，被代理类和代理类是继承关系，所以代理类是可以赋值给被代理类的
 * CGLIB代理使用字节码处理框架asm，对代理对象类的class文件加载进来，通过修改字节码生成子类
 * CGLIB创建代理对象效率较低，执行效率高
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/1/31 16:52
 */
public class CglibDynamicProxy implements MethodInterceptor {

    /**
     * 代理的目标对象
     */
    private Object object;

    public CglibDynamicProxy(Object object) {
        this.object = object;
    }

    public Object proxy() {
        Class<?> clazz = object.getClass();
        // 生成代理对象
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(proxy.getClass().getSuperclass().getName());
        beforeMethod(object);
        Object result = methodProxy.invokeSuper(proxy, objects);
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

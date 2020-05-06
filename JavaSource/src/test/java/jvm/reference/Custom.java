package jvm.reference;

/**
 * 创建一个类
 * 重写finalize方法
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/21 16:59
 */
public class Custom {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
    }
}

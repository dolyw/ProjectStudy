package com.design.prototype;

/**
 * 原型模式 - 克隆，拷贝
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/1 17:32
 */
public class Main {

    public static void main(String[] args) throws Exception {
        StringBuffer address = new StringBuffer("深圳市南山区XXX");
        Location location = new Location(address, "XXX村");
        Person wangMing = new Person("李明", 20, location);
        // 克隆wangMing
        Person liMing = (Person) wangMing.clone();
        // 修改原始对象
        wangMing.setName("王明");
        wangMing.setAge(30);
        address.reverse();
        location.setRemark("XXX路");
        // 输出原始和克隆对象，正常应该是深拷贝
        // 修改原始对象，克隆对象还是刚开始声明的属性
        // 将Person重写的clone方法对Location的克隆注释放开
        // 以及Location重写的clone方法对StringBuffer的克隆注释放开
        // 不然就会出现浅拷贝的问题
        System.out.println(wangMing);
        System.out.println(liMing);
    }

}

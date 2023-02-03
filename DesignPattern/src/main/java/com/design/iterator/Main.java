package com.design.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 迭代器模式 - JDK的容器的Iterator
 * 有兴趣可以研究源码，自己实现
 *
 * @author wliduo[i@dolyw.com]
 * @date 2023/2/2 16:22
 */
public class Main {

    public static void main(String[] args) {
        Collection collection = new ArrayList();
        for (int i = 0; i < 10; i++) {
            collection.add(new String("s" + i));
        }
        // 遍历
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

}

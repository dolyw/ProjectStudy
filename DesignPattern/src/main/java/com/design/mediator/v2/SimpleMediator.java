package com.design.mediator.v2;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单中介者(调停者) - 单例
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 14:29
 */
public class SimpleMediator {

    private static final SimpleMediator INSTANCE = new SimpleMediator();

    private SimpleMediator() { }

    public static SimpleMediator getInstance() {
        return INSTANCE;
    }

    /**
     * 存放注册的子系统
     */
    private List<SubSystem> subSystemList = new ArrayList<>();

    /**
     * 注册子系统
     *
     * @param subSystem
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/5 14:37
     */
    public void register(SubSystem subSystem) {
        if (!subSystemList.contains(subSystem)) {
            subSystemList.add(subSystem);
        }
    }

    /**
     * 当前系统转发消息到其他系统
     *
     * @param subSystem
	 * @param message
     * @return void
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/5 15:24
     */
    public void relay(SubSystem subSystem, String message) {
        subSystemList.forEach(o -> {
            if (!o.equals(subSystem)) {
                o.receive(message);
            }
        });
    }

}

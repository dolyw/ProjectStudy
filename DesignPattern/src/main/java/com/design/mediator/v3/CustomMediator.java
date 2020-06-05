package com.design.mediator.v3;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体中介者
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/6/5 15:22
 */
public class CustomMediator extends AbstractMediator {

    /**
     * 存放注册的子系统
     */
    private List<AbstractSubSystem> subSystemList = new ArrayList<>();

    @Override
    public void register(AbstractSubSystem subSystem) {
        if (!subSystemList.contains(subSystem)) {
            subSystemList.add(subSystem);
            // 设置中介者到当前系统
            subSystem.setMediator(this);
        }
    }

    @Override
    public void relay(AbstractSubSystem subSystem, String message) {
        subSystemList.forEach(o -> {
            if (!o.equals(subSystem)) {
                o.receive(message);
            }
        });
    }
}

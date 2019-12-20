package com.demo.service;

import com.demo.IDemoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * DemoServiceImpl
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/12/20 10:10
 */
@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements IDemoService {

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        System.out.println(name + "连接成功");
        return serviceName + ": " + name;
    }

}

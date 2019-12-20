package com.demo.controller;

import com.demo.IDemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoController
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/12/20 11:08
 */
@RestController
@RequestMapping
public class DemoController {

    @Reference(version = "${demo.service.version}")
    private IDemoService demoService;

    @GetMapping("/")
    public String sayHello() {
        String text = demoService.sayHello("Hello");
        System.out.println(text);
        return text;
    }

    @GetMapping("/demo")
    public String sayXXX(@RequestParam("name") String name) {
        String text = demoService.sayHello(name);
        System.out.println(text);
        return text;
    }

}

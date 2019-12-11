package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通用访问匹配页面跳转
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/1/24 19:27
 */
@Controller
public class IndexController {

    /**
     * 通用页面跳转
     *
     * @param url
     * @return java.lang.String
     * @author wliduo[i@dolyw.com]
     * @date 2019/1/24 19:27
     */
    @RequestMapping("{url}.shtml")
    public String page(@PathVariable("url") String url) {
        return url;
    }

    /**
     * 通用页面跳转(二级目录)
     *
     * @param module
	 * @param url
     * @return java.lang.String
     * @author wliduo[i@dolyw.com]
     * @date 2019/1/24 19:27
     */
    @RequestMapping("{module}/{url}.shtml")
    public String page(@PathVariable("module") String module, @PathVariable("url") String url) {
        return module + "/" + url;
    }

    /**
     * 通用页面跳转(三级目录)
     *
     * @param module
	 * @param url
     * @return java.lang.String
     * @author wliduo[i@dolyw.com]
     * @date 2019/1/25 19:35
     */
    @RequestMapping("{module}/{module2}/{url}.shtml")
    public String page(@PathVariable("module") String module, @PathVariable("module2") String module2,
                       @PathVariable("url") String url) {
        return module + "/" + module2 + "/" + url;
    }

}

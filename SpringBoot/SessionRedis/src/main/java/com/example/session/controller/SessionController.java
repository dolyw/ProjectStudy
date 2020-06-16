package com.example.session.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SessionController
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/19 14:46
 */
@RestController
@RequestMapping("/")
public class SessionController {

    /**
     * 测试Session共享
     *
     * @param request
     * @return java.lang.String
     * @throws
     * @author wliduo[i@dolyw.com]
     * @date 2020/6/15 18:15
     */
    @GetMapping(value = "/session")
    public String getSession(HttpServletRequest request) {
        String msg = "";
        HttpSession session = request.getSession();
        if (session.getAttribute("msg") != null) {
            return session.getAttribute("msg").toString();
        } else {
            session.setAttribute("msg", "Hello");
        }
        return msg;
    }

}

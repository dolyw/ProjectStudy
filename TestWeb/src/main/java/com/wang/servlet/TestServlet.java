package com.wang.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Desc 测试热部署
 * @Author Wang926454
 * @Date 2018/5/10 15:11
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

    private String message;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doGet(req, resp);
        // 设置响应内容类型
        resp.setContentType("text/html;charset=UTF-8");
        System.out.println("测试热部署");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPost(req, resp);
        doGet(req, resp);
    }

    @Override
    public void destroy() {
        // super.destroy();
        System.out.println("TestServlet销毁");
    }

    @Override
    public void init() throws ServletException {
        // super.init();
        System.out.println("TestServlet启动");
    }
}

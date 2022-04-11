package com.easymall.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//注销servlet
@WebServlet(name = "LogOutServlet", value = "/LogOutServlet")
public class LogOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //注销用户就是销毁session
        if (request.getSession(false)!=null){
            request.getSession().invalidate();
            //清空30天自动登录cookie
            Cookie cookie = new Cookie("autologin","");
            cookie.setMaxAge(0);
            cookie.setPath(request.getContextPath()+"/");
            response.addCookie(cookie);
        }
        //跳转回首页
        response.sendRedirect(request.getContextPath()+"/");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

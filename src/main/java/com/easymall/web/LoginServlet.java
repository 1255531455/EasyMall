package com.easymall.web;

import com.easymall.domain.User;
import com.easymall.exception.MsgException;
import com.easymall.service.UserService;
import com.easymall.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//登录Servlet
@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //乱码处理
//        request.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
        //1.获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //获取是否记住用户名选项
        String remname = request.getParameter("remname");//"true"
        //获取30天是否登录
        String autologin = request.getParameter("autologin");//"true"or"null"
        //2.记住用户名--cookie
        //remname为"true"则需要记住用户名
        if ("true".equals(remname)){
            Cookie cookie = new Cookie("remname", URLEncoder.encode(username,"utf-8"));
            cookie.setMaxAge(60*60*24*30);//30天记住用户名
            cookie.setPath(request.getContextPath()+"/");
            response.addCookie(cookie);
        }else {
            Cookie cookie = new Cookie("remname","");
            cookie.setMaxAge(0);
            cookie.setPath(request.getContextPath()+"/");
            response.addCookie(cookie);
        }
        

        //判断是否需要自动登录，如果需要则创建一个自动登录的cookie，保存用户信息30天
        if ("true".equals(autologin)){
            Cookie cookie = new Cookie("autologin",
                    URLEncoder.encode(username,"utf-8")+"#"+password);
            cookie.setMaxAge(60*60*24*30);
            cookie.setPath(request.getContextPath()+"/");
            response.addCookie(cookie);
        }else {

        }
//        //3.保存用户登录状态---sesssion
//        //判断用户名与密码是否匹配
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = JDBCUtils.getConnection();
//            ps = conn.prepareStatement("select * from user where username=? and password=?");
//            ps.setString(1,username);
//            ps.setString(2,password);
//            rs = ps.executeQuery();
//            if (rs.next()){//正确完成登录
//                HttpSession session = request.getSession();
//                session.setAttribute("username",username);
//            }else {//返回登录页面做出提示
//                request.setAttribute("msg","用户名或密码不正确");
//                request.getRequestDispatcher("login.jsp").forward(request,response);
//                return;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JDBCUtils.close(conn,ps,rs);
//        }

        //创建USerService
        UserService userService = new UserService();
        try {
            User user = userService.loginUser(username,password);
            //得到user对象，应放入session域，保留登录状态
            request.getSession().setAttribute("user",user);
        } catch (MsgException e) {
            //发生异常则证明，需要向前台页面输出错误提示信息
            request.setAttribute("msg",e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            e.printStackTrace();
        }

        //4.跳转回首页
        response.sendRedirect("http://www.easymall.com");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

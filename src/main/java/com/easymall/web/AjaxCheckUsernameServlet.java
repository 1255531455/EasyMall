package com.easymall.web;

import com.easymall.domain.User;
import com.easymall.exception.MsgException;
import com.easymall.service.UserService;
import com.easymall.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//ajax请求，查询数据库中用户名是否已存在
@WebServlet(name = "AjaxCheckUsernameServlet", value = "/AjaxCheckUsernameServlet")
public class AjaxCheckUsernameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.乱码处理
//        request.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
        //2.获取参数
        String username = request.getParameter("username");
//        //3.访问数据查询用户名是否存在
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            conn = JDBCUtils.getConnection();
//            ps = conn.prepareStatement("select * from user where username = ?");
//            ps.setString(1,username);
//            rs = ps.executeQuery();
//            if (rs.next()){//用户名已存在，提示用户更改用户名
//                response.getWriter().write("用户名已存在");
//            }else {//用户名不存在可以使用
//                response.getWriter().write("用户名可以使用");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            JDBCUtils.close(conn,ps,rs);
//        }
        User user = new User();
        user.setUsername(username);
        UserService userService = new UserService();
        try {
            userService.registUser(user);
            response.getWriter().write("用户名可以使用");
        } catch (MsgException e) {
            response.getWriter().write(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

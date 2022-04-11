package com.easymall.web;

import com.easymall.domain.User;
import com.easymall.exception.MsgException;
import com.easymall.service.UserService;
import com.easymall.utils.JDBCUtils;
import com.easymall.utils.WebUtils;

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

@WebServlet(name = "RegistServlet", value = "/RegistServlet")
public class RegistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //请求乱码处理
//        request.setCharacterEncoding("utf-8");
//        //响应乱码处理
//        response.setContentType("text/html;charset=utf-8");
        //2.请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String valistr = request.getParameter("valistr");

        //3.非空校验
        if (WebUtils.isNULL(username)){
            //将错误提示放入request域
            request.setAttribute("msg","用户名不能为空");
            //使用请求转发，将request转发到regist.jsp页面
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            //打断注册
            return;
        }
        if (WebUtils.isNULL(password)){
            //将错误提示放入request域
            request.setAttribute("msg","密码不能为空");
            //使用请求转发，将request转发到regist.jsp页面
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            //打断注册
            return;
        }
        if (WebUtils.isNULL(password2)){
            //将错误提示放入request域
            request.setAttribute("msg","确认密码不能为空");
            //使用请求转发，将request转发到regist.jsp页面
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            //打断注册
            return;
        }
        if (WebUtils.isNULL(nickname)){
            //将错误提示放入request域
            request.setAttribute("msg","昵称不能为空");
            //使用请求转发，将request转发到regist.jsp页面
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            //打断注册
            return;
        }
        if (WebUtils.isNULL(email)){
            //将错误提示放入request域
            request.setAttribute("msg","邮箱不能为空");
            //使用请求转发，将request转发到regist.jsp页面
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            //打断注册
            return;
        }
        if (WebUtils.isNULL(valistr)){
            //将错误提示放入request域
            request.setAttribute("msg","验证码不能为空");
            //使用请求转发，将request转发到regist.jsp页面
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            //打断注册
            return;
        }
        //4.密码一致性校验
        if (password != null && password2 != null && !password.equals(password2)){
            request.setAttribute("msg","两次密码不一致");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }
        //5.邮箱格式校验
        //van_d@163.com
        String reg = "\\w+@\\w+(\\.\\w+)+";
        if (email!=null && !email.matches(reg)){
            request.setAttribute("msg","邮箱格式不正确");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }
        //6.验证码校验
        //获取域中的验证码和用户输入的验证码，做比对
        String code = (String) request.getSession().getAttribute("code");
        if (!code.equalsIgnoreCase(valistr)){
            request.setAttribute("msg","验证码错误");
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }
        //TODD:session
        //7.完成注册
        //连接池
        //用户名是否存在校验
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        Connection conn = null;
//        try {
//            conn = JDBCUtils.getConnection();
//            ps = conn.prepareStatement("select * from user where username=?");
//            ps.setString(1,username);
//            rs = ps.executeQuery();
//            if (rs.next()){ //用户已存在，再页面中作出提示
//                request.setAttribute("msg","用户名已存在");
//                request.getRequestDispatcher("/regist.jsp").forward(request,response);
//                return;
//            }else { //用户名不存在，完成注册
//                conn = JDBCUtils.getConnection();
//                ps = conn.prepareStatement("insert into user values(null,?,?,?,?)");
//                ps.setString(1,username);
//                ps.setString(2,password);
//                ps.setString(3,nickname);
//                ps.setString(4,email);
//                ps.executeUpdate();
//                ps.executeBatch();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JDBCUtils.close(conn,ps,rs);
//        }

        //将数据发送到service层
        UserService userService = new UserService();
        //参数传递内容较多，可以封装再Javabean中，所以创建User类来封装数据
        User user = new User(0,username,password,nickname,email);
        //把错误注册的信息，当作异常来处理，所以此处可以不添加返回值
        try {
            userService.registUser(user);
        } catch (MsgException e) {
            //获取异常对象身上提供的异常信息，作为页面提示
            request.setAttribute("msg",e.getMessage());
            request.getRequestDispatcher("/regist.jsp").forward(request,response);
            return;
        }
        //8.跳转回首页
        response.getWriter().write("<h1 align='center'><font color='red'>恭喜注册成功，3秒后跳转回首页</font></h1>");
        response.setHeader("refresh","3;url=http://www.easymall.com");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }
}

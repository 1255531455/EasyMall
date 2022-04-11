package com.easymall.filter;

import com.easymall.domain.User;
import com.easymall.exception.MsgException;
import com.easymall.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

//30天自动登录过滤器
@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        UserService userService = new UserService();
        HttpServletRequest req = (HttpServletRequest) request;
        //cookie读取用户信息autologin
        //1.获取全部cookie
        Cookie[] cs = req.getCookies();
        //2.遍历cookie，从中寻找autologin，自动登录cookie
        Cookie autologinC = null;
        if (cs != null){
            for (Cookie c:cs){
                if ("autologin".equals(c.getName())){
                    autologinC = c;
                }
            }
        }
        //找到了autologin，取出用户名，与密码和数据库中的数据比对
        if (autologinC != null){
            //取出用户名密码
            String value = URLDecoder.decode(autologinC.getValue(),"utf-8");
            //更具#切割
            String[] vs = value.split("#");
            String username = vs[0];
            String password = vs[1];

            try {
                //与数据库中的数据比对
                User user = userService.loginUser(username, password);
                //保存登录状态--添加到session域
                req.getSession().setAttribute("user",user);
            } catch (MsgException e) {
//                throw new RuntimeException(e);
                e.printStackTrace();
            }
        }
        chain.doFilter(req, response);
    }
}

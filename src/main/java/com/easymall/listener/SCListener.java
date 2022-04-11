package com.easymall.listener;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.easymall.domain.User;
import org.apache.log4j.*;

@WebListener
public class SCListener implements ServletRequestListener,ServletContextListener {
    public static Logger logger = Logger.getLogger(String.valueOf(SCListener.class));
    public SCListener() {
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        //1.获取请求对象
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        //2.获取请求对象的参数ip-url
        String username = "游客";
        if(request.getSession(false)!=null && request.getSession().getAttribute("user")!=null){
            User user = (User) request.getSession().getAttribute("user");
            username = user.getUsername();
        }
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
//        System.out.println("用户【"+username+"】访问地址【"+url+"】客户机ip【"+ip+"】请求结束");
        logger.debug("用户【"+username+"】访问地址【"+uri+"】客户机ip【"+ip+"】请求结束");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        //1.获取请求对象
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        //2.获取请求对象的参数ip-url
        String username = "游客";
        if(request.getSession(false)!=null && request.getSession().getAttribute("user")!=null){
            User user = (User) request.getSession().getAttribute("user");
            username = user.getUsername();
        }
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
//        System.out.println("用户【"+username+"】访问地址【"+url+"】客户机ip【"+ip+"】请求开始");
        logger.debug("用户【"+username+"】访问地址【"+uri+"】客户机ip【"+ip+"】请求开始");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("EasyMall启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("EasyMall销毁");
    }
}

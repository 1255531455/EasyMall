package com.easymall.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//全站乱码过滤器
@WebFilter(filterName = "EncodingFilter",urlPatterns = "/*")
public class EncodingFilter implements Filter {
    String encode = null;
    boolean use_encode;
    public void init(FilterConfig config) throws ServletException {
        //在当前过滤器配置信息对象的身上
        // 可以引出代表web应用的对象servletContext，这样即可获取全局配置信息
        encode = config.getServletContext().getInitParameter("encode");
        //读取字符集是否使用的开关
        use_encode = Boolean.parseBoolean(config.getServletContext().getInitParameter("use_encode"));
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //1.请求乱码处理post get
//        request.setCharacterEncoding("utf-8");
        HttpServletRequest req = use_encode?
                new MyHttpServletRequest((HttpServletRequest) request,encode): (HttpServletRequest) request;
        //2.响应乱码
        response.setContentType("text/html;charset="+encode);
        //放行
        chain.doFilter(req, response);
    }
}

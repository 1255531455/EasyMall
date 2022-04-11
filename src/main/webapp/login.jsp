<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css"/>
    <title>EasyMall欢迎您登陆</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery-3.6.0.js"></script>
    <script type="text/javascript">
        //文档就绪事件
        $(function (){
            //为username输入框绑定cookie中的value值
            $("input[name='username']").val(decodeURI('${cookie.remname.value}'));
        });
    </script>
</head>
<body>
<h1>欢迎登陆EasyMall</h1>
<form action="${pageContext.request.contextPath}/LoginServlet" method="POST">
    <table>
<%--    <%--%>
<%--        //获取cookie中的remname 读取其中的用户名--%>
<%--        Cookie[] cs = request.getCookies();--%>
<%--        Cookie remnameC = null;--%>
<%--        //初次获取cookie数组为null--%>
<%--        if (cs!=null){--%>
<%--            for(Cookie c:cs){//遍历cookie数组，寻找remname这个cookie--%>
<%--                if ("remname".equals(c.getName())){--%>
<%--                    remnameC = c;--%>
<%--                }--%>
<%--            }--%>
<%--        }--%>
<%--        String username = "";--%>
<%--        if (remnameC!=null){--%>
<%--            username = URLDecoder.decode(remnameC.getValue(),"utf-8");--%>
<%--        }--%>
<%--    %>--%>
        <tr>
            <td class="tdx" colspan="2" style="color: red;text-align: center">
                ${requestScope.msg}</td>
        </tr>
        <tr>
            <td class="tdx">用户名：</td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td class="tdx">密&nbsp;&nbsp; 码：</td>
            <td><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td colspan="2">
<%--                <%="".equals(username)?"":"checked='checked'"%>--%>
                <input type="checkbox" name="remname" value="true"
                 ${not empty cookie.remname.value?"checked='checked'":""}
                />记住用户名
                <input type="checkbox" name="autologin" value="true"/>30天内自动登陆
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center">
                <input type="submit" value="登 陆"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
